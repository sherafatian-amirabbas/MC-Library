package com.example.library

import android.os.Bundle
import android.view.View
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

        rvResult.setHasFixedSize(true)
        val bookAdapter = BookAdapter()
        rvResult.adapter = bookAdapter

        viewModel.books.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    progressResult.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    progressResult.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    bookAdapter.submitList(it.data)
                    progressResult.visibility = View.GONE
                }
            }
        }

        toolbarResult.setNavigationOnClickListener {
            finish()
        }
    }
}