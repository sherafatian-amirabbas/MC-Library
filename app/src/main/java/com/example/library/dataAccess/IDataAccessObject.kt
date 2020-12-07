package com.example.library.dataAccess

import androidx.room.*
import com.example.library.dataAccess.entities.Favorite
import com.example.library.dataAccess.entities.UserSetting

@Dao
interface IDataAccessObject {

    @Query("SELECT * FROM userSettings")
    fun getUserSetting(): Array<UserSetting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSetting(vararg userSettings: UserSetting)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserSetting(vararg userSettings: UserSetting)


    // ------------------------------------------------- Favorites

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorite(favorite: Favorite)

    @Delete
    fun removeFromFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<Favorite>

    @Query("SELECT * FROM favorites WHERE title LIKE :keyword OR description LIKE :keyword OR author LIKE :keyword OR ISBN LIKE :keyword")
    fun getFavoritesBy(keyword: String): List<Favorite>

    @Query("SELECT * FROM favorites WHERE id=:favoriteId")
    fun getFavorite(favoriteId: String): Favorite?
}