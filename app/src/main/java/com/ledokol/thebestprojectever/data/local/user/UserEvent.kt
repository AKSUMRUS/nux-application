package com.ledokol.thebestprojectever.data.local.user

sealed class UserEvent {
    object Refresh: UserEvent() // Обновить данные по всем пользователям
    data class OnSearchQueryChange(val query: String): UserEvent() // Поиск пользователей по совпадению имени
    data class GetFriendUser(val nickname: String): UserEvent() // Получить информацию об аккаунте друга
}