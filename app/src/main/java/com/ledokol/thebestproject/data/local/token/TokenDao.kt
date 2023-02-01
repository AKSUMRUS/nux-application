package com.ledokol.thebestproject.data.local.token

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TokenDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToken(token: TokenEntity)

    @Query(""" DELETE FROM "token" """)
    fun clearToken()

    @Query("""SELECT * FROM "token" LIMIT 1 """)
    fun getToken(): TokenEntity?

}