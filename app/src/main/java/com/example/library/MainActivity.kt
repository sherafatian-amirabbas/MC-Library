package com.example.library

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            val keyword = edtSearch.text.toString().trim()
            if (keyword.isEmpty())
                Toast.makeText(this, getString(R.string.fill_search_box), Toast.LENGTH_SHORT).show()
            else {
                startResultActivity(keyword)
            }
        }
    }

    private fun startResultActivity(keyword: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(KEYWORD_KEY, keyword)
        startActivity(intent)
    }
}