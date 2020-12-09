package com.example.library.dataAccess.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
class Log (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val key: String,
    val info: String,
)