package com.ledokol.thebestprojectever.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GamesDao
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDao
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao

@Database(
    entities = [(Profile::class),(User::class),(Game::class)],
    version = 3
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun usersDao(): UsersDao
    abstract fun gamesDao(): GamesDao
}