package com.ledokol.thebestprojectever.data.mapper

import com.ledokol.thebestprojectever.data.local.UserEntity
import com.ledokol.thebestprojectever.domain.module.User

fun UserEntity.toUser(): User {
    return User(
        isLogined = isLogined,
        nickname = nickname,
        phoneNumber = phoneNumber
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        isLogined = isLogined,
        nickname = nickname,
        phoneNumber = phoneNumber
    )
}