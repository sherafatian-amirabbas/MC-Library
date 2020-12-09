package com.example.library.dataAccess.repository

import com.example.library.dataAccess.DataModel
import android.content.Context

class Repository(var context: Context) {

    private var _repo = DataModel.getInstance(context).getDataAccessObject()

    val User = RepositoryUser(context, _repo)
    val Favorite = RepositoryFavorite(context, _repo)
    val Log = RepositoryLog(context, _repo)
}