package com.example.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = intent
        intent?.let {
            val keyword = it.getStringExtra(KEYWORD_KEY)
            keyword?.let {
                toolbarResult.title = keyword
            }
        }

        toolbarResult.setNavigationOnClickListener {
            finish()
        }
    }
}