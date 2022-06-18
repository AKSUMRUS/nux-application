package com.ledokol.thebestprojectever.data.local.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ledokol.thebestprojectever.data.local.profile.ProfileDatabase


@Database(
    entities = [(User::class)],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao() : UsersDao

    companion object{
        private var INSTANCE: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase {
            synchronized(this){
                var instance = UsersDatabase.INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "users_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}