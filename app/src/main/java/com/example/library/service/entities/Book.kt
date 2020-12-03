package com.example.library.service.entities

import java.util.*

data class Book(val Id: String,
                val Title: String,
                val Description: String,
                val Abstract: String,
                val ISBN: String,
                val Author: String,
                val Publisher: String,
                val CreationDate: Date
) {}