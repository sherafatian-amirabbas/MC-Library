package com.example.library

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.Common
import com.example.library.ui.FavoriteAdapter
import com.example.library.ui.viewModel.FavoriteViewModel
import com.example.library.ui.viewModel.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), (String) -> Unit {
    lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbarFavorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarFavorite.setNavigationOnClickListener { finish() }
        val factory = FavoriteViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        rvFavorite.setHasFixedSize(true)

        viewModel.getFavorites().observe(this) {
            favoriteAdapter = FavoriteAdapter(ArrayList(it), this)
            rvFavorite.adapter = favoriteAdapter
        }
    }

    override fun invoke(favoriteId: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Common.BOOK_ID_KEY, favoriteId)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val favoriteItem = menu?.findItem(R.id.menu_favorite)
        favoriteItem?.isVisible = false
        val refreshItem = menu?.findItem(R.id.menu_refresh)
        refreshItem?.isVisible = false

        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getFavorites(newText).observe(this@FavoriteActivity) {
                    favoriteAdapter.updateList(it)
                }
                return true
            }
        })
        return true
    }
}