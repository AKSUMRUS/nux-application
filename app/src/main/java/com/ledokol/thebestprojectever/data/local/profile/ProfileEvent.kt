package com.ledokol.thebestprojectever.data.local.profile

import android.graphics.Bitmap
import okhttp3.MultipartBody
import okhttp3.RequestBody

sealed class ProfileEvent{
    object GetProfile: ProfileEvent()
    object LogOut: ProfileEvent()
    object GetDefaultProfilePics: ProfileEvent()
    data class InviteFriends(
        val accessToken: String,
        val friends_ids: List<String>,
        val app_id: String
        ) : ProfileEvent()
    data class UpdateAvatar(val accessToken: String, val profile_pic: Bitmap): ProfileEvent()
    data class ConfirmationPhone(val phone: String, val reason: String): ProfileEvent()
    data class SetCurrentFirebaseToken(val token: String, val accessToken: String) : ProfileEvent()
    data class SetFinishRegister(val accessToken: String) : ProfileEvent()
    data class UpdateProfileData(val newProfile: Profile) : ProfileEvent()
    data class Login(val phone: String, val id: String, val code: String) : ProfileEvent()
    data class SignUp(
        val nickname: String,
        val name: String,
        val phone: String,
        val default_profile_pic_id: String,
        val id: String,
        val code: String,
        ) : ProfileEvent()
    data class SetDoNotDisturb(
        val canDisturb: Boolean,
        val accessToken: String
    ) : ProfileEvent()
}
