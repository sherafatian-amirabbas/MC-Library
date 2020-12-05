package com.example.library.dataAccess

import androidx.lifecycle.LiveData
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favorite: Favorite?)

    @Delete
    suspend fun removeFromFavorite(favorite: Favorite?)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): LiveData<List<Favorite>>

    @Query("SELECT COUNT(*) FROM favorites WHERE id=:favoriteId")
    suspend fun isFavorite(favoriteId: String?): Int
}