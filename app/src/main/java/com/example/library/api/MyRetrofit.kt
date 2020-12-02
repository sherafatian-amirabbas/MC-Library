package com.example.library.api

import com.example.library.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit private constructor() {
    companion object {
        private var instance: Retrofit? = null

        private fun getInstance(): Retrofit {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return instance!!
        }

        fun getService(): ApiService {
            return getInstance().create(ApiService::class.java)
        }
    }
}