package com.ledokol.thebestprojectever.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileDao
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.local.user.UsersDao

@Database(
    entities = [(Profile::class),(User::class)],
    version = 2
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun usersDao(): UsersDao
}