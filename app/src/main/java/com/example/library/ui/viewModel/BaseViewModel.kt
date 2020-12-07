package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.library.dataAccess.repository.Repository
import com.example.library.service.LibraryProxy


open class BaseViewModel(var context: Context) : ViewModel() {

    var repository = Repository(context)
    var service = LibraryProxy(context)
}