package com.ledokol.thebestprojectever.data.local.user

data class Status(
    val last_update: String = "",
    val started_at: String =  "",
    val finished: Boolean = true,
    val id: String = "",
    val current_app: CurrentApp? = CurrentApp()
)