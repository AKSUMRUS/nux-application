package com.nux.studio.dvor.data.local.user

data class Status(
    val id: String = "",
    val app: CurrentApp? = null,
    val dt_last_update: String = "",
    val dt_entered_app: String = "",
    val dt_leaved_app: String = "",
    val in_app: Boolean = true,
    val online: Boolean = true,
)