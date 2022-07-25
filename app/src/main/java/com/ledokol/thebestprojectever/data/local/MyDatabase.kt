package com.ledokol.thebestprojectever.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.data.local.contact.ContactsDao
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GamesDao
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDao
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao

@Database(
    entities = [(Profile::class),(User::class),(Game::class),(Contact::class)],
    version = 8
)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun usersDao(): UsersDao
    abstract fun gamesDao(): GamesDao
    abstract fun contactsDao(): ContactsDao
}