package com.example.library

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.Common
import com.example.library.ui.BookAdapter
import com.example.library.ui.viewModel.MainViewModel
import com.example.library.ui.viewModel.MainViewModelFactory
import com.example.library.workmanager.LibraryWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {

    private lateinit var bookAdapter: BookAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _repo.Log.deleteLogs()
        var a = _repo.Log.getLogs()

        initialize()
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateModel("", {})
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
            startFavoritesActivity()
            true
        }


        return true
    }

    private fun startFavoritesActivity() {
        val intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }

    // ---------------------- private members
    private fun initialize() {

        LibraryWorker.setup(this)

        setSupportActionBar(toolbarMain)

        initializeAdapter()
        initializeRecycler()
        initializeViewModel()
    }

    private fun initializeAdapter() {
        bookAdapter = BookAdapter(ArrayList(), {

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(Common.extraKeys.BOOK_ID_KEY, it)
            intent.putExtra(Common.extraKeys.DETAIL_TYPE_KEY, false)
            startActivity(intent)
        })
    }

    private fun initializeRecycler() {
        rvMain.setHasFixedSize(true)
        rvMain.adapter = bookAdapter
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(this.application))
            .get(MainViewModel::class.java)

        viewModel.books.observe(this) {
            bookAdapter.updateBooks(it)
        }
    }
}