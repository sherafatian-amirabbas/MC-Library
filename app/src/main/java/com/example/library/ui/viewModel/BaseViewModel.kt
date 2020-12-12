package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.library.businessLogic.Repository
import com.example.library.common.service.LibraryProxy


open class BaseViewModel(var context: Context) : ViewModel() {

    var repository = Repository(context)
    var service = LibraryProxy(context)
}