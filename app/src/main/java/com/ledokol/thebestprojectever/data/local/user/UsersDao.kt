package com.ledokol.thebestprojectever.data.local.user

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)

    @Insert
    fun insertUser(user: User)

    @Query(
        """
            SELECT * 
            FROM users
            WHERE LOWER(nickname) LIKE '%' || LOWER(:query) || '%'
        """
    )
    fun getUsers(query: String) : List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int) : User

    @Query("DELETE FROM users")
    fun clearUsers()

}