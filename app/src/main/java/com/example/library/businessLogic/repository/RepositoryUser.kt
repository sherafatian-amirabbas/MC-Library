package com.example.library.businessLogic.repository;

import android.content.Context
import com.example.library.businessLogic.repository.RepositoryBase
import com.example.library.common.Common
import com.example.library.dataAccess.IDataAccessObject
import com.example.library.dataAccess.entities.UserSetting


class RepositoryUser(context: Context, repo: IDataAccessObject):
    RepositoryBase(context, repo)  {

    fun getUserSetting(): UserSetting? {

        var settings = repo.getUserSetting()
        if (settings.size != 0)
            return settings[0]
        else
            return null
    }

    fun updateLastVisitDate(onComplete: (UserSetting) -> Unit) {
        var userSetting = getUserSetting()

        var setting =
            if (userSetting == null)
                UserSetting(1, "", 15, true)
            else
                userSetting!!

        _libraryProxy.getServerDate {

            setting.lastVisitDate = Common.convertDateToStringFormat(it)
            upsertUserSetting(setting)

            if (onComplete != null)
                onComplete(setting)
        }
    }

    fun updateServiceSetting(serviceIntervalInMinutes: Int,
         isServiceEnabled: Boolean,
         onComplete: (UserSetting) -> Unit) {

        var userSetting = getUserSetting()

        if (userSetting == null) {

            updateLastVisitDate({

                it.serviceIntervalInMinutes = serviceIntervalInMinutes
                it.isServiceEnabled = isServiceEnabled

                upsertUserSetting(it)

                onComplete(it)
            })
        } else {
            userSetting.serviceIntervalInMinutes = serviceIntervalInMinutes
            userSetting.isServiceEnabled = isServiceEnabled

            upsertUserSetting(userSetting)

            onComplete(userSetting)
        }
    }


// ----------------------------- private members

    private fun upsertUserSetting(userSetting: UserSetting) {

        var original_setting = getUserSetting()
        if (original_setting == null)
            repo.insertUserSetting(userSetting)
        else
            repo.updateUserSetting(userSetting)
    }
}