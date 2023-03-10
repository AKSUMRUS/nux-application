package com.nux.studio.dvor.data.local.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)

    @Query("SELECT * FROM profile LIMIT 1")
    fun getProfile(): Profile?

    @Query("DELETE FROM profile")
    fun clearProfile()

    @Query("UPDATE profile SET finish_register=:finish_register WHERE access_token=:access_token")
    fun finishRegister(access_token: String, finish_register: Boolean = true)


}