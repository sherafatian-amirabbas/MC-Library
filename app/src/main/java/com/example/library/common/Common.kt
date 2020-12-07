package com.example.library.common

import java.lang.Long
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*
import java.util.regex.Pattern

class Common {

    companion object
    {
        var BOOK_ID_KEY = "_BOOK_ID_EXTRA_"
        var DETAIL_TYPE_KEY = "_IS_FAVORITE_MODE_"

        // to remove non-digit characters "/Date(xxxxxxxxxxxxxx)/"
        final var non_digit_regex = Regex("[^0-9]+")
        fun convertTicksFormatDateIntoDate(ticksFormatDate: String) : Date {

            var ticks = ticksFormatDate.replace(non_digit_regex, "")
            val stamp = Timestamp(Long.parseLong(ticks))
            val date = Date(stamp.getTime())
            return date
        }

        var dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        fun convertDateToLastVisitDateStringFormat(date: Date) : String
        {
            return dateFormat.format(date)
        }
        fun getLastVisitDateFromLastVisitDateStringFormat(lastVisitDate: String): Date
        {
            var date = dateFormat.parse(lastVisitDate)
            return date
        }
    }
}