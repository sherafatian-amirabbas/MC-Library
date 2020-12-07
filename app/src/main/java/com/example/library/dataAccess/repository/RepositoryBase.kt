package com.example.library.dataAccess.repository

import android.content.Context
import com.example.library.dataAccess.IDataAccessObject
import com.example.library.service.LibraryProxy


open class RepositoryBase(var context: Context, var repo: IDataAccessObject) {

    var _libraryProxy = LibraryProxy(context)
}