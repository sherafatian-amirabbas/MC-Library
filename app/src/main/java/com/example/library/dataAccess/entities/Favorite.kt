package com.example.library.dataAccess.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.library.service.entities.Book

@Entity(tableName = "favorites")
data class Favorite(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var Id: String,

    @ColumnInfo(name = "title")
    var Title: String,

    @ColumnInfo(name = "description")
    var Description: String,

    @ColumnInfo(name = "abstract")
    var Abstract: String,

    @ColumnInfo(name = "ISBN")
    var ISBN: String,

    @ColumnInfo(name = "author")
    var Author: String,

    @ColumnInfo(name = "publisher")
    var Publisher: String
)
{
    @Ignore
    var isExpired: Boolean = false


    @Ignore
    fun fillFromBook(book: Book?)
    {
        book?.let {

            this.Id = it.Id
            this.Title = it.Title
            this.Description = it.Description
            this.Abstract = it.Abstract
            this.ISBN = it.ISBN
            this.Author = it.Author
            this.Publisher = it.Publisher
        }
    }
}