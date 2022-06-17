package com.ledokol.thebestprojectever.data.local

import android.content.pm.ApplicationInfo
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "access_token")
    var access_token: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,

//    var userGames: List<ApplicationInfo>,

)