package com.example.library

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = intent
        intent?.let {
            val keyword = it.getStringExtra(KEYWORD_KEY)
            keyword?.let {
                Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show()
            }
        }
    }
}