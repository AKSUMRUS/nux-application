package com.ledokol.dvor.data.remote

import com.ledokol.dvor.data.local.notifications.NotificationEntity
import com.ledokol.dvor.data.local.profile.DoNotDisturb
import com.ledokol.dvor.data.local.profile.Profile
import com.ledokol.dvor.data.local.profile.ProfileToken
import com.ledokol.dvor.data.local.user.User
import com.ledokol.dvor.domain.games.*
import com.ledokol.dvor.domain.profile.*
import com.ledokol.dvor.domain.statistics.GameSessionStatisticsList
import com.ledokol.dvor.domain.users.AddFriend
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {

    @POST("login")
    fun login(
        @Body profile: LoginJSON
    )
            : Call<ProfileToken>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun createProfile(@Body profile: RegisterJSON)
            : Call<ProfileToken>

    @GET("friends")
    fun getFriends()
            : Call<List<User>>

    @GET("friends")
    fun getFindFriends(
        @Header("Authorization") authHeader: String
    )
            : Call<List<User>>

    @GET("friends")
    fun addFriend(
        @Header("Authorization") authHeader: String,
        @Query("user_id") userId: String,
    )
            : Call<Any>

    @Headers("Content-Type: application/json")
    @GET("user/{id}")
    fun getUser(
        @Path("id") id: String
    )
            : Call<User>

    @PUT("status/in_app/android")
    fun setStatus(
        @Body status: App
    )
            : Call<StatusJSON>

    @PUT("status/not_in_app")
    fun leaveStatus(
    )
            : Call<StatusJSON>

    @PUT("sync_installed_apps/android")
    fun shareGames(
        @Body games: AppsStatus
    )
            : Call<AppsGame>

    @PUT("current_user/firebase_messaging_token")
    fun setCurrentFirebaseToken(
        @Header("Authorization") authHeader: String,
        @Body firebase_messaging_token: FirebaseToken
    )
            : Call<String>

    @GET("apps/friend/{id}/v2")
    fun getUserGames(
        @Path("id") id: String
    )
            : Call<ListApps>

    @GET("apps/current_user/v2")
    fun getMyGames(
    )
            : Call<ListApps>

    @POST("friends/invite")
    fun friendsInvite(
        @Header("Authorization") authHeader: String,
        @Body friends: FriendsInviteToGame
    ): Call<Any>

    @GET("current_user")
    fun getMe(
        @Header("Authorization") authHeader: String
    ): Call<Profile>

    @Multipart
    @PUT("current_user/profile_pic")
    fun uploadAvatar(
        @Header("Authorization") authHeader: String,
        @Part profile_pic: MultipartBody.Part
    ): Call<Profile>

    @GET("app/{app_id}")
    fun getGame(
        @Path("app_id") app_id: String,
    ): Call<Profile>

    @Multipart
    @PUT("app/package/{package_name}/set_images")
    fun pushGamesIcon(
        @Path("package_name") package_name: String,
        @Part icon_preview: MultipartBody.Part,
    ): Call<Any>

    @PUT("current_user/do_not_disturb")
    fun setDoNotDisturb(
        @Header("Authorization") authHeader: String,
        @Body doNotDisturb: DoNotDisturb
    ): Call<Profile>

    @POST("confirmation/phone")
    fun confirmationPhone(
        @Body confirmationPhone: ConfirmationPhone
    ): Call<ResponseConfirmationPhone>

    @GET("users/check")
    fun checkExistsNickname(
        @Query("nickname") nickname: String,
    ): Call<ExistsUserJSON>

    @GET("users/check")
    fun checkExistsPhone(
        @Query("phone") phone: String,
    ): Call<ExistsUserJSON>

    @GET("default_profile_pics/list")
    fun getDefaultProfilePics(
    ): Call<DefaultProfilePicsList>

    @PUT("friends/add")
    fun addFriend(
        @Body addFriend: AddFriend,
    ): Call<String>

    @GET("users")
    fun getUserByNickname(
        @Query("nickname") nickname: String,
    ): Call<User>

    @GET("users")
    fun getUserByPhone(
        @Query("phone") phone: String,
    ): Call<User>

    @GET("friends/pending_invites")
    fun getFriendPendingInvites(
    ): Call<List<NotificationEntity>>


    @PUT("current_user/edit")
    fun updateProfile(
        @Header("Authorization") authHeader: String,
        @Body updateProfile: UpdateProfileJSON
    ): Call<User>

    @DELETE("friends/remove_friend")
    fun removeFriend(
        @Query("friend_id") friend_id: String,
    ): Call<Any>

    @DELETE("friends/reject_invite")
    fun rejectInvite(
        @Query("from_user_id") from_user_id: String,
    ): Call<String>

    @GET("friends/recommended")
    fun getRecommendedFriends(
    ): Call<List<User>>

    @PUT("apps/statistics/update_from_local/android")
    fun putStatistics(
        @Body statistics: GameSessionStatisticsList
    ): Call<Any>

}