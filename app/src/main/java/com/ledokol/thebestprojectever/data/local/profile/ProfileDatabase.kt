package com.ledokol.thebestprojectever.data.local.profile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [(Profile::class)],
    version = 1
)
abstract class ProfileDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        private var INSTANCE: ProfileDatabase? = null

        fun getInstance(context: Context): ProfileDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileDatabase::class.java,
                        "profile_database"
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