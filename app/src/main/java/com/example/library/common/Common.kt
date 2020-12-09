package com.example.library.common

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import java.lang.Long
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class Common {

    companion object
    {
        var extraKeys = ExtraKeys()

        // to remove non-digit characters "/Date(xxxxxxxxxxxxxx)/"
        final var non_digit_regex = Regex("[^0-9]+")
        fun convertTicksFormatDateIntoDate(ticksFormatDate: String) : Date {

            var ticks = ticksFormatDate.replace(non_digit_regex, "")
            val stamp = Timestamp(Long.parseLong(ticks))
            val date = Date(stamp.getTime())
            return date
        }


        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        fun convertDateToStringFormat(date: Date) : String
        {
            return dateFormat.format(date)
        }
        fun getDateFromStringFormat(lastVisitDate: String): Date
        {
            var date = dateFormat.parse(lastVisitDate)
            return date
        }


        fun getLastVisitDate(lastVisitDate: String) : Date? {

            if(lastVisitDate.isNullOrEmpty())
                return null

            return getDateFromStringFormat(lastVisitDate)
        }


        fun isAppOnForeground(context: Context): Boolean {

            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            val appProcesses = activityManager.runningAppProcesses
            if(appProcesses == null)
                return false

            val packageName: String = context.getPackageName()
            for (appProcess in appProcesses) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    appProcess.processName == packageName) {

                    return true
                }
            }

            return false
        }
    }
}

class ExtraKeys()
{
    var BOOK_ID_KEY = "_BOOK_ID_EXTRA_"
    var DETAIL_TYPE_KEY = "_IS_FAVORITE_MODE_"
}