package com.nux.studio.dvor.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertGames(games: List<CurrentApp>?)

    @Query(
        """
            SELECT * 
            FROM users
            WHERE LOWER(nickname) LIKE '%' || LOWER(:query) || '%'
        """
    )
    fun getUsers(query: String): List<User>

    @Query("SELECT * FROM users WHERE nickname = :nickname")
    fun getUser(nickname: String): User

    @Query("DELETE FROM users")
    fun clearUsers()

}