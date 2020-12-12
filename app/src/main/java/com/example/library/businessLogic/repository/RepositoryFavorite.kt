package com.example.library.businessLogic.repository

import android.content.Context
import com.example.library.dataAccess.IDataAccessObject
import com.example.library.dataAccess.entities.Favorite


class RepositoryFavorite(context: Context, repo: IDataAccessObject):
    RepositoryBase(context, repo)  {

    // this will return favorite but updates it first by checking the original data on the web service
    fun getUpdatedFavorite(bookId: String, onComplete: (Favorite?) -> Unit)
    {
        var favorite = getFavorite(bookId)
        if(favorite == null)
            onComplete(favorite)
        else
        {
            _libraryProxy.getBookByID(bookId, {

                favorite.isExpired = it == null
                favorite.fillFromBook(it)

                onComplete(favorite)
            })
        }
    }

    fun isAddedToFavorite(bookId: String): Boolean {
        var favorite = getFavorite(bookId)
        return favorite != null
    }

    fun removeFromFavorite(favorite: Favorite) {
        repo.removeFromFavorite(favorite)
    }

    fun addToFavorite(favorite: Favorite) {
        repo.addToFavorite(favorite)
    }

    // this will return favorites but updates them first by checking the original data on the web service
    fun getUpdatedFavorites(onComplete: (List<Favorite>) -> Unit) {

        var favorites = repo.getFavorites()
        updateFavoritesBasedOnOriginalDataOnService(favorites, {

            onComplete(it)
        })
    }

    // this will return favorites but updates them first by checking the original data on the web service
    fun getUpdatedFavoritesBy(keyword: String, onComplete: (List<Favorite>) -> Unit) {

        var favorites = repo.getFavoritesBy(keyword)
        updateFavoritesBasedOnOriginalDataOnService(favorites, {

            onComplete(it)
        })
    }

    fun getFavorites(): List<Favorite>  {

        return repo.getFavorites()
    }


    // ------------------- private members

    private fun getFavorite(favoriteId: String) : Favorite?
    {
        return repo.getFavorite(favoriteId)
    }

    private fun updateFavoritesBasedOnOriginalDataOnService(favorites: List<Favorite>,
        onComplete: (List<Favorite>) -> Unit)
    {
        _libraryProxy.getAllBooks {

            var list = it
            favorites.forEach {

                var favorite = it

                var book = list.find({ it.Id == favorite.Id })
                favorite.isExpired = book == null
                favorite.fillFromBook(book)
            }

            onComplete(favorites)
        }
    }
}