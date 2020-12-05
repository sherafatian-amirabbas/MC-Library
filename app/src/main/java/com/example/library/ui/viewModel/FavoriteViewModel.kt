package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.dataAccess.Repository
import com.example.library.dataAccess.entities.Favorite

class FavoriteViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(context) as T
    }
}

class FavoriteViewModel(context: Context) : ViewModel() {
    private var repository = Repository(context)

    fun getFavorites(): LiveData<List<Favorite>> {
        return repository.getFavorites()
    }
}