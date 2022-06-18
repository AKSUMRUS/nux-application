package com.ledokol.thebestprojectever.data.local.user

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao{

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getUsers() : LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int) : User

    @Query("DELETE FROM users")
    fun clearUsers()

}