package com.ledokol.thebestproject.data.local.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "status")
    var status: Status = Status(),
    @ColumnInfo(name = "profile_pic")
    var profile_pic: String,
    @ColumnInfo(name = "do_not_disturb")
    var do_not_disturb: String,

)