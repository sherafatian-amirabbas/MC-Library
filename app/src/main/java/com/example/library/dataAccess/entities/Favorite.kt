package com.example.library.dataAccess.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val Id: String,

    @ColumnInfo(name = "title")
    val Title: String,
    @ColumnInfo(name = "description")
    val Description: String,
    @ColumnInfo(name = "abstract")
    val Abstract: String,
    @ColumnInfo(name = "ISBN")
    val ISBN: String,
    @ColumnInfo(name = "author")
    val Author: String,
    @ColumnInfo(name = "publisher")
    val Publisher: String
)