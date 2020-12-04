package com.example.library

import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.Common
import com.example.library.ui.viewModel.DetailsViewModel
import com.example.library.ui.viewModel.DetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.menu_search)
        searchItem?.isVisible = false
        val refreshItem = menu?.findItem(R.id.menu_refresh)
        refreshItem?.isVisible = false
        return true
    }


    // -------------------- initialize

    fun initialize()
    {
        setSupportActionBar(toolbarDetails)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        initializeViewModel()
        updateView()
    }

    fun initializeViewModel()
    {
        viewModel = ViewModelProvider(this, DetailsViewModelFactory(this.application))
            .get(DetailsViewModel::class.java)

        viewModel.book.observe(this) {
            txtTitleBook.text = it.Title
            txtDescBook.text = it.Description
            txtAuthorBook.text = it.Author
            txtISBNBook.text = it.ISBN
            txtAbstractBook.text = it.Abstract
        }
    }

    fun updateView()
    {
        val intent = intent
        if (intent != null) {

            val bookId = intent.getStringExtra(Common.BOOK_ID_KEY)
            viewModel.updateModel(bookId!!)
        }
    }
}