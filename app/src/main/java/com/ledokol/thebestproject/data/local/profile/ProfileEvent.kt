package com.ledokol.thebestproject.data.local.profile

import android.graphics.Bitmap

sealed class ProfileEvent{
    object GetProfile: ProfileEvent()
    object LogOut: ProfileEvent()
    object GetDefaultProfilePics: ProfileEvent()
    class InviteFriends(
        val accessToken: String,
        val friends_ids: List<String>,
        val app_id: String
        ) : ProfileEvent()
    class UpdateAvatar(val accessToken: String, val profile_pic: Bitmap): ProfileEvent()
    class ConfirmationPhone(val phone: String, val reason: String): ProfileEvent()
    class SetCurrentFirebaseToken(val token: String, val accessToken: String) : ProfileEvent()
    class SetFinishRegister(val accessToken: String) : ProfileEvent()
    class UpdateProfileData(val newProfile: Profile) : ProfileEvent()
    class Login(val phone: String, val id: String, val code: String) : ProfileEvent()
    class SignUp(
        val nickname: String,
        val name: String,
        val phone: String,
        val default_profile_pic_id: String,
        val id: String,
        val code: String,
        ) : ProfileEvent()
    class SetDoNotDisturb(
        val canDisturb: Boolean,
        val accessToken: String
    ) : ProfileEvent()
}
