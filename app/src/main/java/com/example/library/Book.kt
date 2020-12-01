package com.example.library

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("ID") val id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Description") val description: String,
    @SerializedName("Abstract") val abstract: String,
    @SerializedName("ISBN") val ISBN: String,
    @SerializedName("Author") val author: String,
    @SerializedName("Publisher") val publisher: String,
    @SerializedName("CreationDate") val creationDate: String,
)