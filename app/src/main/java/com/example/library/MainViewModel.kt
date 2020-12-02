package com.example.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.api.MyRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService = MyRetrofit.getService()
    var books = MutableLiveData<List<Book>>()


    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.getAllBooks()
            books.postValue(result)
        }
    }

    fun searchBooks(newText: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.search(newText)
            books.postValue(result)
        }
    }
}