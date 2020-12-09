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
        intent.putExtra(Common.extraKeys.BOOK_ID_KEY, favoriteId)
        intent.putExtra(Common.extraKeys.DETAIL_TYPE_KEY, true)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)

        val homeMenuItem = menu?.findItem(R.id.menu_home)
        homeMenuItem?.setOnMenuItemClickListener {

            startMainActivity()
            true
        }


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


        val settingMenuItem = menu?.findItem(R.id.menu_settings)
        settingMenuItem.setOnMenuItemClickListener {

            startSettingActivity()
            true
        }


        return true
    }

    private fun startSettingActivity() {

        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun startMainActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}