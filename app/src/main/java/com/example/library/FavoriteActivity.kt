package com.example.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.library.ui.FavoriteAdapter
import com.example.library.ui.viewModel.FavoriteViewModel
import com.example.library.ui.viewModel.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), (String) -> Unit {
    lateinit var viewModel: FavoriteViewModel

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
            val favoriteAdapter = FavoriteAdapter(it, this)
            rvFavorite.adapter = favoriteAdapter
        }
    }

    override fun invoke(favoriteId: String) {

    }
}