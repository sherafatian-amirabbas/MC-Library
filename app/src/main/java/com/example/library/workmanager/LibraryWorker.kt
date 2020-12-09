package com.example.library.workmanager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.example.library.BaseActivity
import com.example.library.common.Common
import com.example.library.dataAccess.repository.Repository
import com.example.library.service.LibraryProxy
import com.example.library.ui.notification.NewBooksNotification
import java.util.*
import java.util.concurrent.TimeUnit


class LibraryWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    val _repo = Repository(applicationContext)
    var _libraryProxy = LibraryProxy(context)


    companion object {

        private val Key = "_NOTIFICATION_WORKER_KEY"

        fun setup(context: Context, policy: ExistingPeriodicWorkPolicy)
        {
            val repo = Repository(context)
            var userSetting = repo.User.getUserSetting()
            if(userSetting == null || !userSetting.isServiceEnabled)
                return


            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()


            val workRequest =
                PeriodicWorkRequestBuilder<LibraryWorker>(userSetting!!.serviceIntervalInMinutes.toLong(),
                        TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .build()


            WorkManager.getInstance()
                .enqueueUniquePeriodicWork(
                    LibraryWorker.Key,
                    policy,
                    workRequest
                )
        }

        fun stop()
        {
            WorkManager.getInstance().cancelUniqueWork(LibraryWorker.Key)
        }
    }


    override fun doWork(): Result {

        _libraryProxy.getServerDate {

            val now = it

            if(Common.isAppOnForeground(applicationContext)) {

                var desc = "notification suppressed - App is on the foreground"
                _repo.Log.logLibraryWorker(now, true, desc)

                Log.i("Library", desc)
            }
            else {

                var desc = "LibraryWorker started doWork - not completed yet"
                _repo.Log.logLibraryWorker(now, false, desc)

                Log.i("Library", desc)

                NewBooksNotification.sendNewBooksNotification(applicationContext)
                { result: NewBooksNotification.NewBooksNotificationResult, description: String ->

                    var succeed : Boolean = false
                    if(result == NewBooksNotification.NewBooksNotificationResult.Successful ||
                        result == NewBooksNotification.NewBooksNotificationResult.NoNewBooksIsAdded)
                        succeed = true

                    var log = _repo.Log.logLibraryWorker(now, succeed, description)

                    Log.i("Library", log.toString())
                }
            }
        }


        return Result.success()
    }
}