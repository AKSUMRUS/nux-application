package com.ledokol.thebestprojectever.data.local.user

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "nickname")
    var nickname: String,

)