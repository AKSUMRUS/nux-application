package com.ledokol.thebestprojectever.data.local.profile

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
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "finish_register")
    var finish_register: Boolean = false,
//    @ColumnInfo(name = "lastGame")
//    var lastGame: String,

//    var userGames: List<ApplicationInfo>,

)