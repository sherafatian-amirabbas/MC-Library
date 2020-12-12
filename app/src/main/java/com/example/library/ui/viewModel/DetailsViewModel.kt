package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.common.service.entities.Book
import java.util.*


class DetailsViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(context) as T
    }
}

class DetailsViewModel(contex: Context) : BaseViewModel(contex) {

    var book = MutableLiveData<Book>()
    var isAddedToFavorite = MutableLiveData<Boolean>()
    var isExpired = MutableLiveData<Boolean>()


    fun updateModel(bookId: String, isFavoriteMode: Boolean) {

        isAddedToFavorite.value = repository.Favorite.isAddedToFavorite(bookId)

        if(isFavoriteMode) {

            repository.Favorite.getUpdatedFavorite(bookId, {

                var fav = it!!
                book.value =
                    Book(fav.Id, fav.Title, fav.Description, fav.Abstract, fav.ISBN, fav.Author, fav.Publisher, Date())

                isExpired.value = fav.isExpired
            })
        }
        else {

            service.getBookByID(bookId, {

                isExpired.value = false
                book.value = it!!
            })
        }
    }

    fun toggleFavorites() {

        if(book.value == null) return

        val favorite = book.value!!.toFavorite()
        if (isAddedToFavorite.value == true) {

            repository.Favorite.removeFromFavorite(favorite)
            isAddedToFavorite.postValue(false)

        } else {

            repository.Favorite.addToFavorite(favorite)
            isAddedToFavorite.postValue(true)
        }
    }
}