package com.example.library.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit private constructor() {
    companion object {
        private var instance: Retrofit? = null
        private const val BASE_URL = "http://www.tu-library.somee.com/"

        private fun getInstance(): Retrofit {
            if (instance == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build()
            }
            return instance!!
        }

        fun getService(): ApiService {
            return getInstance().create(ApiService::class.java)
        }
    }
}