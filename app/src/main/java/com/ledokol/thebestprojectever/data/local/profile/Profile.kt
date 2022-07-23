package com.ledokol.thebestprojectever.data.local.profile

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ledokol.thebestprojectever.data.local.user.Status

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "access_token")
    var access_token: String,
    @ColumnInfo(name = "nickname")
    var nickname: String = "",
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "id")
    var id: String = "",
    @ColumnInfo(name = "status")
    var status: Status? = Status(),
    @ColumnInfo(name = "finish_register")
    var finishRegister: Boolean = false,
    @ColumnInfo(name = "profile_pic")
    var profile_pic: String? = "",
    @ColumnInfo(name = "do_not_disturbe_mode")
    var do_not_disturbe_mode: Boolean = false,
    @ColumnInfo(name = "phone")
    var phone: String? = "",

//    @ColumnInfo(name = "lastGame")
//    var lastGame: String,

//    var userGames: List<ApplicationInfo>,

)