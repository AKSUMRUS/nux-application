package com.ledokol.thebestprojectever.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey
    @ColumnInfo(name = "access_token")
    var access_token: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,
)