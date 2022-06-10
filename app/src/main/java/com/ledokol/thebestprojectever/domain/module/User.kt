package com.ledokol.thebestprojectever.domain.module

data class User(
    val isLogined: Boolean = false,
    val nickname: String,
    val phoneNumber: String,
)
