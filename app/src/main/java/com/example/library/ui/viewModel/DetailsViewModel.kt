package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.library.dataAccess.Repository
import com.example.library.service.LibraryProxy
import com.example.library.service.entities.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(context) as T
    }
}

class DetailsViewModel(contex: Context) : ViewModel() {

    private var service = LibraryProxy(contex)
    private var repository = Repository(contex)
    var book = MutableLiveData<Book>()
    var isFavorite = MutableLiveData<Boolean>()

    fun updateModel(bookId: String) {

        service.getBookByID(bookId, {
            book.value = it!!
        })
    }

    fun isFavorite(bookId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultCount = repository.isFavorite(bookId)
            isFavorite.postValue(resultCount > 0)
        }
    }
}