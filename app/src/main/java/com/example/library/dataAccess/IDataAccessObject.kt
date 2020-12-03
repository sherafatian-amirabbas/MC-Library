package com.example.library.dataAccess

import androidx.room.*
import com.example.library.dataAccess.entities.UserSetting

@Dao
interface IDataAccessObject {

    @Query("SELECT * FROM userSettings")
    fun getUserSetting(): Array<UserSetting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSetting(vararg userSettings: UserSetting)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUserSetting(vararg userSettings: UserSetting)
}