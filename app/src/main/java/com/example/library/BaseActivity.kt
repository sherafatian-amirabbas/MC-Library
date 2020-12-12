package com.example.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.library.businessLogic.Repository

open class BaseActivity: AppCompatActivity() {

    lateinit var _repo: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _repo = Repository(applicationContext)
    }
}