package com.example.library

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.library.api.Status
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)

        val intent = intent
        intent?.let {
            val keyword = it.getStringExtra(KEYWORD_KEY)
            keyword?.let {
                toolbarResult.title = keyword
                viewModel.searchBooks(keyword)
            }
        }

        viewModel.books.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    for (book in it.data!!) {
                        Log.d("MyLog", book.title)
                    }
                }
            }
        }

        toolbarResult.setNavigationOnClickListener {
            finish()
        }
    }
}