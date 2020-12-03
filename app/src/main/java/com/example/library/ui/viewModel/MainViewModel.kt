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


class MainViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}

class MainViewModel(context: Context) : ViewModel() {

    private var _repo = Repository(context)
    private var service = LibraryProxy(context)
    var books = MutableLiveData<List<Book>>()

    fun updateModel(searchText: String, onComplete: () -> Unit)
    {
        if(searchText.isNullOrEmpty())
            service.getAllBooks {

                books.value = it
                onComplete()

                _repo.updateLastVisitDate {  }
            }
        else
            service.getBooksByKeywork(searchText, {

                books.value = it
                onComplete()
            })
    }
}