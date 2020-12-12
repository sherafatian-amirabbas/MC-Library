package com.example.library.businessLogic.repository

import android.content.Context
import com.example.library.dataAccess.IDataAccessObject
import com.example.library.common.service.LibraryProxy


open class RepositoryBase(var context: Context, var repo: IDataAccessObject) {

    var _libraryProxy = LibraryProxy(context)
}