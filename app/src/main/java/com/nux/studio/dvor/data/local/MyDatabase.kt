package com.nux.studio.dvor.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nux.studio.dvor.data.local.contact.Contact
import com.nux.studio.dvor.data.local.contact.ContactsDao
import com.nux.studio.dvor.data.local.game.Game
import com.nux.studio.dvor.data.local.game.GamesDao
import com.nux.studio.dvor.data.local.notifications.NotificationEntity
import com.nux.studio.dvor.data.local.notifications.NotificationsDao
import com.nux.studio.dvor.data.local.profile.Profile
import com.nux.studio.dvor.data.local.profile.ProfileDao
import com.nux.studio.dvor.data.local.token.TokenDao
import com.nux.studio.dvor.data.local.token.TokenEntity
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.data.local.user.UsersDao

@Database(
    entities = [(Profile::class), (User::class), (Game::class), (Contact::class), (NotificationEntity::class), (TokenEntity::class)],
    version = 10
)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun usersDao(): UsersDao
    abstract fun gamesDao(): GamesDao
    abstract fun contactsDao(): ContactsDao
    abstract fun notificationDao(): NotificationsDao
    abstract fun tokenDao(): TokenDao
}