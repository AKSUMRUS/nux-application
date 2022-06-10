package com.ledokol.thebestprojectever.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(
        userEntity: UserEntity
    )

    @Query("SELECT * FROM userEntity")
    suspend fun searchUser(): UserEntity

    @Query("DELETE FROM userEntity")
    suspend fun clearUser()

}