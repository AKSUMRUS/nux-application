package com.ledokol.thebestprojectever.data.local.user

sealed class UserEvent {
    object Refresh: UserEvent()
    data class OnSearchQueryChange(val query: String): UserEvent()
}