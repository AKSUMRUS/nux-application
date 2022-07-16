package com.ledokol.thebestprojectever.data.local.profile

data class ProfileState (
    val profile: Profile? = null,
    val lastGame: String = "",
    val finish_register: Boolean = false,
    val isLodaing: Boolean = false
)