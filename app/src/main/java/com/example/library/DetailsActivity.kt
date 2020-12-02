package com.example.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        val intent = intent
        if (intent != null) {
            val bookId = intent.getStringExtra(BOOK_ID_KEY)
            viewModel.getBookById(bookId!!)
        }

        viewModel.book.observe(this) {
            txtTitleBook.text = it.title
            txtDescBook.text = it.description
            txtAuthorBook.text = it.author
            txtISBNBook.text = it.ISBN
            txtAbstractBook.text = it.abstract
        }
    }
}