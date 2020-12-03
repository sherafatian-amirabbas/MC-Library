package com.example.library.dataAccess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.library.dataAccess.entities.UserSetting

@Database(entities = [UserSetting::class], version = 1)
abstract class DataModel : RoomDatabase() {

    companion object {

        lateinit var dbInstance: DataModel

        @Synchronized
        fun getInstance(context: Context): DataModel {

            if(!this::dbInstance.isInitialized) {

                dbInstance = Room.databaseBuilder(
                    context,
                    DataModel::class.java,
                    "ut-library"
                )
                    .fallbackToDestructiveMigration() // each time schema changes, data is lost!
                    .allowMainThreadQueries() // if possible, use background thread instead
                    .build()
            }

            return dbInstance
        }
    }

    abstract fun getDataAccessObject(): IDataAccessObject
}