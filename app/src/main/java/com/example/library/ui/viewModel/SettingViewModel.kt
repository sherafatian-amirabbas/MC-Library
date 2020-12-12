package com.example.library.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.dataAccess.entities.UserSetting


class SettingViewModelFactory(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingViewModel(context) as T
    }
}

class SettingViewModel(contex: Context) : BaseViewModel(contex) {

    var userSetting = MutableLiveData<UserSetting>()


    fun updateModel() {

        userSetting.value = repository.User.getUserSetting()!!
    }
}