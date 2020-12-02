package com.example.library

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        val intent = intent
        if (intent != null) {
            val bookId = intent.getStringExtra(BOOK_ID_KEY)
            viewModel.getBookById(bookId!!)
        }

        viewModel.book.observe(this) {
            Log.d("MyLog", it.title)
        }
    }
}