package com.example.library.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
    var book = MutableLiveData<Book>()

    fun updateModel(bookId: String) {

        service.getBookByID(bookId, {

            book.value = it!!
        })
    }
}