package com.example.library.api

import com.example.library.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("Book/GetAllByKeyword/")
    suspend fun search(
        @Path("keyword") keyword: String
    ): List<Book>
}