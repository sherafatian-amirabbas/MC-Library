package com.example.library.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.library.MainActivity
import com.example.library.R
import com.example.library.common.Common
import com.example.library.dataAccess.repository.Repository
import com.example.library.service.LibraryProxy
import java.util.*

class NewBooksNotification(val context: Context, val Title: String, val Text: String) {

    private val _channelId = "_library_notification_channel_id_"
    private val _channelName = "New Books Availability Notification"
    private val _channelDesc = "this will keep the users up to date regarding new books get available in the library."

    private val _notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    companion object{

        fun sendNewBooksNotification(context: Context,
            onComplete: (NewBooksNotificationResult, Description: String) -> Unit)
        {
            var _repo = Repository(context)
            var _libraryProxy = LibraryProxy(context)

            var userSetting = _repo.User.getUserSetting()
            if(userSetting != null)
            {
                var lastVisitDate = Common.getLastVisitDate(userSetting.lastVisitDate)
                if(lastVisitDate != null)
                {
                    var lastCheckedDate = lastVisitDate!!

                    var lastExecutionDate = _repo.Log.getLastLibraryWorkerExecutionDate()
                    if(lastExecutionDate != null && lastExecutionDate!! > lastCheckedDate)
                        lastCheckedDate = lastExecutionDate

                    _libraryProxy.getServerDate {

                        val now = it
                        var toDate =
                            _libraryProxy.getBooksByDateRange(lastCheckedDate, now, {

                                var resources = context.getResources()
                                var title = resources.getString(R.string.NotificationTitle)
                                var text = resources.getString(R.string.NotificationText, it.size)

                                if(it.size != 0) {

                                    NewBooksNotification(context, title, text).show()
                                    onComplete(NewBooksNotificationResult.Successful, text)
                                }
                                else
                                    onComplete(NewBooksNotificationResult.NoNewBooksIsAdded,
                                        "no new books is added from ${ Common.convertDateToStringFormat(lastCheckedDate) } - to ${ Common.convertDateToStringFormat(now) } - size is 0")
                            })
                    }
                }
                else
                    onComplete(NewBooksNotificationResult.LastVisitDateIsNull, "lastVisitDate is null")
            }
            else
                onComplete(NewBooksNotificationResult.UserSettingIsNull, "userSetting is null")
        }
    }


    fun show(): NewBooksNotification
    {
        createChannel()
        var pendingIntent = getPendingIntent()

        var builder = NotificationCompat.Builder(context, _channelId)
            .setContentTitle(Title)
            .setContentText(Text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, com.example.library.R.drawable.book))
            .setSmallIcon(com.example.library.R.drawable.favorites)

        _notificationManager.notify(1, builder.build())

        return this
    }

    private fun createChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(_channelId, _channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = _channelDesc
            _notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getPendingIntent() : PendingIntent?
    {
        val resultIntent = Intent(context, MainActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {

            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        return resultPendingIntent
    }


    enum class NewBooksNotificationResult(val result: Int) {
        UserSettingIsNull(1),
        LastVisitDateIsNull(2),
        NoNewBooksIsAdded(4),
        Successful(8)
    }
}