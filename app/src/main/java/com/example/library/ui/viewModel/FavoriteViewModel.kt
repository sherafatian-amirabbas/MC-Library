package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.dataAccess.entities.Favorite

class FavoriteViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(context) as T
    }
}

class FavoriteViewModel(context: Context) : BaseViewModel(context) {

    var favorites = MutableLiveData<List<Favorite>>()

    fun updateModel(keyword: String?, onComplete: () -> Unit) {

        if (keyword.isNullOrEmpty())
            return repository.Favorite.getUpdatedFavorites({

                favorites.value = it
                onComplete()
            })
        else
            return repository.Favorite.getUpdatedFavoritesBy("%$keyword%", {

                favorites.value = it
                onComplete()
            })
    }
}