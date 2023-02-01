package com.ledokol.thebestproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ledokol.thebestproject.data.local.contact.Contact
import com.ledokol.thebestproject.data.local.contact.ContactsDao
import com.ledokol.thebestproject.data.local.game.Game
import com.ledokol.thebestproject.data.local.game.GamesDao
import com.ledokol.thebestproject.data.local.notifications.NotificationEntity
import com.ledokol.thebestproject.data.local.notifications.NotificationsDao
import com.ledokol.thebestproject.data.local.profile.Profile
import com.ledokol.thebestproject.data.local.profile.ProfileDao
import com.ledokol.thebestproject.data.local.token.TokenDao
import com.ledokol.thebestproject.data.local.token.TokenEntity
import com.ledokol.thebestproject.data.local.user.User
import com.ledokol.thebestproject.data.local.user.UsersDao

@Database(
    entities = [(Profile::class),(User::class),(Game::class),(Contact::class), (NotificationEntity::class),(TokenEntity::class)],
    version = 10
)
@TypeConverters(Converters::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun usersDao(): UsersDao
    abstract fun gamesDao(): GamesDao
    abstract fun contactsDao(): ContactsDao
    abstract fun notificationDao(): NotificationsDao
    abstract fun tokenDao() : TokenDao
}