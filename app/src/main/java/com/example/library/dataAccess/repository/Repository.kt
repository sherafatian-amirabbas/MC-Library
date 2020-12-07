package com.example.library.dataAccess.repository

import com.example.library.dataAccess.DataModel
import android.content.Context

class Repository(var context: Context) {

    private var _repo = DataModel.getInstance(context).getDataAccessObject()

    var User = RepositoryUser(context, _repo)
    var Favorite = RepositoryFavorite(context, _repo)
}