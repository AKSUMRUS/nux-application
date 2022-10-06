package com.ledokol.thebestproject.data.local.user

sealed class UserEvent {
    class Refresh(val shouldReload:Boolean = true): UserEvent() // Обновить данные по всем пользователям
    class OnSearchQueryChange(val query: String): UserEvent() // Поиск пользователей по совпадению имени
    class OnSearchQueryChangeFindFriend(val query: String): UserEvent() // Поиск новых друзей по совпадению имени
    class GetFriendUser(val id: String): UserEvent() // Получить информацию об аккаунте друга
    class SelectUser(val user: User): UserEvent()
    class GetFriendGames(val user: User): UserEvent() // Получить игры пользователя
    class CheckExistsNickname(val nickname: String): UserEvent() // Получить игры пользователя
    class CheckExistsPhone(val phone: String): UserEvent() // Получить игры пользователя
    class AddFriend(val nickname: String? = null, val phone: String? = null, val access_token: String): UserEvent()
    class GetUserByNickname(val nickname: String): UserEvent()
    class GetUserByPhone(val phone: String): UserEvent()
    class OpenScreen(val screen: String): UserEvent()
    class RemoveFriend(val friendId: String): UserEvent()
    class RejectInvite(val userId: String): UserEvent()
    class OpenFriendViaLink(val userId: String): UserEvent()
    object AddFriendById : UserEvent()
    object ClearError : UserEvent()
    object ClearFriendUser: UserEvent()
}

