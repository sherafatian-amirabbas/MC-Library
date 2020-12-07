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

class FavoriteActivity : BaseActivity(), (String) -> Unit {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)


        setSupportActionBar(toolbarFavorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        toolbarFavorite.setNavigationOnClickListener { finish() }


        rvFavorite.setHasFixedSize(true)


        favoriteAdapter = FavoriteAdapter(arrayListOf(), this)
        rvFavorite.adapter = favoriteAdapter


        val factory = FavoriteViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        viewModel.favorites.observe(this@FavoriteActivity) {

            favoriteAdapter.updateList(it)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateModel("", {})
    }

    override fun invoke(favoriteId: String) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Common.BOOK_ID_KEY, favoriteId)
        intent.putExtra(Common.DETAIL_TYPE_KEY, true)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)

        val favoriteItem = menu?.findItem(R.id.menu_favorite)
        favoriteItem?.isVisible = false


        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewModel.favorites.observe(this@FavoriteActivity) {

                    favoriteAdapter.updateList(it)
                }

                viewModel.updateModel(if (newText.isNullOrEmpty()) "" else newText, {})
                return true
            }
        })


        val refreshItem = menu?.findItem(R.id.menu_refresh)
        refreshItem.setOnMenuItemClickListener {

            viewModel.updateModel("", {

                searchItem.collapseActionView()
            })

            true
        }


        return true
    }
}