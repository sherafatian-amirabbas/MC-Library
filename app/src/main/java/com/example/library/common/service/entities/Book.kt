package com.example.library.common.service.entities

import com.example.library.dataAccess.entities.Favorite
import java.util.*

data class Book(val Id: String,
                val Title: String,
                val Description: String,
                val Abstract: String,
                val ISBN: String,
                val Author: String,
                val Publisher: String,
                val CreationDate: Date
) {

    var isAddedAsFavorites: Boolean = false

    fun toFavorite(): Favorite {

        return Favorite(
            Id = Id,
            Title = Title,
            Description = Description,
            Abstract = Abstract,
            ISBN = ISBN,
            Author = Author,
            Publisher = Publisher
        )
    }
}