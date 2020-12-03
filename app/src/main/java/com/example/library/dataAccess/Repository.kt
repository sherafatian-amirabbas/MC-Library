package com.example.library.dataAccess

import android.content.Context
import com.example.library.common.Common
import com.example.library.service.LibraryProxy
import com.example.library.dataAccess.entities.UserSetting
import java.text.SimpleDateFormat
import java.util.*

class Repository(var context: Context) {

    private var _proxy = LibraryProxy(context)
    private var _repo = DataModel.getInstance(context).getDataAccessObject()

    fun getUserSetting(): UserSetting? {

        var settings = _repo.getUserSetting()
        if(settings.size != 0)
            return settings[0]
        else
            return null
    }

    fun updateLastVisitDate(onComplete: (UserSetting) -> Unit)
    {
        var userSetting = getUserSetting()

        var setting =
            if(userSetting == null)
                UserSetting(1,"", 15, true)
            else
                userSetting!!

        _proxy.getServerDate {

            setting.lastVisitDate = Common.convertDateToLastVisitDateStringFormat(it)
            upsertUserSetting(setting)

            if(onComplete != null)
                onComplete(setting)
        }
    }

    fun updateServiceSetting(serviceIntervalInMinutes: Int, isServiceEnabled: Boolean)
    {
        var userSetting = getUserSetting()

        if(userSetting == null) {

            updateLastVisitDate({

                it.serviceIntervalInMinutes = serviceIntervalInMinutes
                it.isServiceEnabled = isServiceEnabled

                upsertUserSetting(it)
            })
        }
        else
        {
            userSetting.serviceIntervalInMinutes = serviceIntervalInMinutes
            userSetting.isServiceEnabled = isServiceEnabled

            upsertUserSetting(userSetting)
        }
    }


    // ----------------------------- private members

    private fun upsertUserSetting(userSetting: UserSetting) {

        var original_setting = getUserSetting()
        if(original_setting == null)
            _repo.insertUserSetting(userSetting)
        else
            _repo.updateUserSetting(userSetting)
    }
}