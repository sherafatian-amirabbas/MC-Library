package com.example.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.library.api.MyRetrofit
import com.example.library.api.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {
    private val apiService = MyRetrofit.getService()

    var books = MutableLiveData<Resource<List<Book>>>()

    fun searchBooks(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            books.postValue(Resource.loading())
            try {
                val result = apiService.search(keyword)
                books.postValue(Resource.success(result))
            } catch (e: Exception) {
                books.postValue(Resource.error(e.message))
            }
        }
    }
}