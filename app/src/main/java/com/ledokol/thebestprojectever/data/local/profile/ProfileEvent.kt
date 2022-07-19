package com.ledokol.thebestprojectever.data.local.profile

sealed class ProfileEvent{
    object GetProfile: ProfileEvent()
    object LogOut: ProfileEvent()
    data class InviteFriends(
        val accessToken: String,
        val friends_ids: List<String>,
        val app_id: String
        ) : ProfileEvent()
    data class SetCurrentFirebaseToken(val token: String) : ProfileEvent()
    data class SetFinishRegister(val accessToken: String) : ProfileEvent()
    data class UpdateProfileData(val newProfile: Profile) : ProfileEvent()
    data class Login(val nickname: String, val password: String) : ProfileEvent()
    data class SignUp(
        val nickname: String,
        val password: String,
        val email: String,
        val name: String
        ) : ProfileEvent()
}
