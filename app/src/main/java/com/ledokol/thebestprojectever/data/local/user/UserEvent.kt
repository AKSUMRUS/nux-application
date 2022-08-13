package com.ledokol.thebestprojectever.data.local.user

sealed class UserEvent {
    class Refresh(val shouldReload:Boolean = true): UserEvent() // Обновить данные по всем пользователям
    class OnSearchQueryChange(val query: String): UserEvent() // Поиск пользователей по совпадению имени
    class OnSearchQueryChangeFindFriend(val query: String): UserEvent() // Поиск новых друзей по совпадению имени
    class GetFriendUser(val id: String): UserEvent() // Получить информацию об аккаунте друга
    class SelectUser(val user: User): UserEvent()
    class GetFriendGames(val user: User): UserEvent() // Получить игры пользователя
    class CheckExistsNickname(val nickname: String): UserEvent() // Получить игры пользователя
    class CheckExistsPhone(val phone: String): UserEvent() // Получить игры пользователя
}

