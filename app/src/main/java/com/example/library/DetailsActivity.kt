package com.example.library

import android.os.Bundle
import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.Common
import com.example.library.ui.viewModel.DetailsViewModel
import com.example.library.ui.viewModel.DetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {

    private lateinit var viewModel: DetailsViewModel
    private var bookId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        initialize()

        viewModel.isFavorite(bookId)

        viewModel.isFavorite.observe(this) {
            if (it) {
                btnAddRemoveFavorite.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                btnAddRemoveFavorite.text = getString(R.string.remove_from_favorites)
            } else {
                btnAddRemoveFavorite.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                btnAddRemoveFavorite.text = getString(R.string.add_to_favorites)
            }
        }
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

    fun initialize() {
        setSupportActionBar(toolbarDetails)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        initializeViewModel()
        updateView()
    }

    fun initializeViewModel() {
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

    fun updateView() {
        val intent = intent
        if (intent != null) {

            bookId = intent.getStringExtra(Common.BOOK_ID_KEY)
            viewModel.updateModel(bookId!!)
        }
    }
}