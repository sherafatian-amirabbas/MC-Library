package com.example.library.businessLogic

import com.example.library.dataAccess.DataModel
import android.content.Context
import com.example.library.businessLogic.repository.RepositoryFavorite
import com.example.library.businessLogic.repository.RepositoryLog
import com.example.library.businessLogic.repository.RepositoryUser

class Repository(var context: Context) {

    private var _repo = DataModel.getInstance(context).getDataAccessObject()

    val User = RepositoryUser(context, _repo)
    val Favorite = RepositoryFavorite(context, _repo)
    val Log = RepositoryLog(context, _repo)
}