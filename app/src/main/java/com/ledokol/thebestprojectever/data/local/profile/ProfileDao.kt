package com.ledokol.thebestprojectever.data.local.profile

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)

    @Query("SELECT * FROM profile")
    fun getProfile(): LiveData<List<Profile>>

    @Query("DELETE FROM profile")
    fun clearProfile()



}