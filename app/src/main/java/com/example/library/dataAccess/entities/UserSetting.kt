package com.example.library.dataAccess.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "userSettings")
data class UserSetting (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var lastVisitDate : String,
    var serviceIntervalInMinutes: Int,
    var isServiceEnabled: Boolean
)