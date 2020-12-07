package com.example.library

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.Common
import com.example.library.ui.viewModel.DetailsViewModel
import com.example.library.ui.viewModel.DetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_favorite.view.*


class DetailsActivity : BaseActivity() {

    private lateinit var viewModel: DetailsViewModel
    private var isFavoriteMode: Boolean = false

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

        val favoriteItem = menu?.findItem(R.id.menu_favorite)
        favoriteItem?.setOnMenuItemClickListener {

            startFavoritesActivity()
            true
        }

        return true
    }


    // -------------------- initialize

    private fun initialize() {

        setSupportActionBar(toolbarDetails)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarDetails.setNavigationOnClickListener { finish() }

        initializeViewModel()
        updateView()


        btnAddRemoveFavorite.setOnClickListener {

            viewModel.toggleFavorites()

            if(isFavoriteMode && viewModel.isExpired.value == true)
                finish()
        }
    }

    private fun initializeViewModel() {

        viewModel = ViewModelProvider(this, DetailsViewModelFactory(this.application))
            .get(DetailsViewModel::class.java)

        viewModel.book.observe(this) {

            txtTitleBook.text = it.Title
            txtDescBook.text = it.Description
            txtAuthorBook.text = it.Author
            txtISBNBook.text = it.ISBN
            txtAbstractBook.text = it.Abstract
        }

        viewModel.isAddedToFavorite.observe(this) {

            if (it) {

                btnAddRemoveFavorite.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                btnAddRemoveFavorite.text = getString(R.string.remove_from_favorites)

            } else {

                btnAddRemoveFavorite.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                btnAddRemoveFavorite.text = getString(R.string.add_to_favorites)
            }
        }

        viewModel.isExpired.observe(this){

            textView_notAvailable.visibility = if(it) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun updateView() {

        this.setTitle(R.string.DetailTitle)

        val intent = intent
        if (intent != null) {

            var bookId = intent.getStringExtra(Common.BOOK_ID_KEY)!!

            // if the page opens from the list of favorites
            isFavoriteMode = intent.getBooleanExtra(Common.DETAIL_TYPE_KEY, false)!!

            viewModel.updateModel(bookId, isFavoriteMode)
        }
    }

    private fun startFavoritesActivity() {

        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
        finish()
    }
}