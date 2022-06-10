package com.ledokol.thebestprojectever.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val isLogined: Boolean = false,
    val nickname: String,
    val phoneNumber: String,
    @PrimaryKey val id: Int? = null,
)
