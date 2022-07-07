package com.ledokol.thebestprojectever.data.local.user

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ledokol.thebestprojectever.domain.StatusJSON

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "status")
    var status: Status = Status()

)