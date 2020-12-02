package com.example.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.api.MyRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val apiService = MyRetrofit.getService()
    var book = MutableLiveData<Book>()

    fun getBookById(bookId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = apiService.getBookById(bookId)
            book.postValue(result)
        }
    }
}