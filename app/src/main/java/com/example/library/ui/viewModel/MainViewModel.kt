package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.service.entities.Book


class MainViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}

class MainViewModel(context: Context) : BaseViewModel(context) {

    var books = MutableLiveData<List<Book>>()

    fun updateModel(searchText: String, onComplete: () -> Unit)
    {
        if(searchText.isNullOrEmpty())
            service.getAllBooks {

                repository.User.updateLastVisitDate {  }

                books.value = updateIsAddedAsFavoriteOnList(it)
                onComplete()
            }
        else
            service.getBooksByKeywork(searchText, {

                books.value = updateIsAddedAsFavoriteOnList(it)
                onComplete()
            })
    }


    // ------------------------------ private members

    private fun updateIsAddedAsFavoriteOnList(books: List<Book>): List<Book>
    {
        var favorites = repository.Favorite.getFavorites()
        books.forEach {

            var book = it
            var favorite = favorites.find({ it.Id == book.Id })
            book.isAddedAsFavorites = favorite != null
        }

        return books
    }
}