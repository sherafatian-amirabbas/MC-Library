package com.example.library

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.Common
import com.example.library.service.LibraryProxy
import com.example.library.ui.BookAdapter
import com.example.library.ui.viewModel.MainViewModel
import com.example.library.ui.viewModel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var bookAdapter: BookAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var service = LibraryProxy(applicationContext)
        service.getServerDate {

            var a = it;

        }


        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.updateModel(if (newText.isNullOrEmpty()) "" else newText, {})
                return true
            }
        })


        val refreshMenuItem = menu.findItem(R.id.menu_refresh)
        refreshMenuItem.setOnMenuItemClickListener {
            viewModel.updateModel("", {
                searchItem.collapseActionView()
            })
            true
        }

        val favoritesMenuItem = menu.findItem(R.id.menu_favorite)
        favoritesMenuItem.setOnMenuItemClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            true
        }


        return true
    }

    // ---------------------- private members
    fun initialize() {
        setSupportActionBar(toolbarMain)

        initializeAdapter()
        initializeRecycler()
        initializeViewModel()
    }

    fun initializeAdapter() {
        bookAdapter = BookAdapter(ArrayList(), {

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(Common.BOOK_ID_KEY, it)
            startActivity(intent)
        })
    }

    fun initializeRecycler() {
        rvMain.setHasFixedSize(true)
        rvMain.adapter = bookAdapter
    }

    fun initializeViewModel() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(this.application))
            .get(MainViewModel::class.java)
        viewModel.books.observe(this) {
            bookAdapter.updateBooks(it)
        }

        viewModel.updateModel("", {})
    }
}