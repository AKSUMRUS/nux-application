package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.game.GameIcons
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileToken
import com.ledokol.thebestprojectever.data.local.user.Apps
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.domain.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {

    @FormUrlEncoded
    @POST("token")
    fun login(
        @Field("username") nickname: String,
        @Field("password") password: String )
    : Call<ProfileToken>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun createProfile(@Body profile: ProfileJSON)
            : Call<ProfileToken>

    @GET("friends")
    fun getFriends(
        @Header("Authorization") authHeader: String
    )
            : Call<List<User>>

    @Headers("Content-Type: application/json")
    @GET("user/{id}")
    fun getUser(
        @Path("id") id: String
    )
            : Call<User>

    @PUT("status/in_app/android")
    fun setStatus(
        @Header("Authorization") authHeader: String,
        @Body status: App
    )
            : Call<StatusJSON>

    @PUT("status/not_in_app")
    fun leaveStatus(
        @Header("Authorization") authHeader: String
    )
            : Call<StatusJSON>

    @PUT("sync_installed_apps/android")
    fun shareGames(
        @Header("Authorization") authHeader: String,
        @Body games: AppsStatus
    )
            : Call<AppsGame>

    @PUT("current_user/firebase_messaging_token")
    fun setCurrentFirebaseToken(
        @Header("Authorization") authHeader: String,
        @Body firebase_messaging_token: FirebaseToken
    )
            :Call<String>

    @GET("user/{id}/apps")
    fun getUserGames(
        @Path("id") id: String
    )
            :Call<Apps>


    @POST("friends/invite")
    fun friendsInvite(
        @Header("Authorization") authHeader: String,
        @Body friends: FriendsInviteToGame
    ) : Call<Any>

    @GET("get_me")
    fun getMe(
        @Header("Authorization") authHeader: String
    ) : Call<Profile>

    @Multipart
    @PUT("current_user/profile_pic")
    fun uploadAvatar(
        @Header("Authorization") authHeader: String,
        @Part profile_pic: MultipartBody.Part
    ): Call<Profile>

    @GET("app/{app_id}")
    fun getGame(
        @Path("app_id") app_id: String,

    ) : Call<Profile>

    @Multipart
    @PUT("app/package/{package_name}/set_images")
    fun pushGamesIcon(
        @Header("Authorization") authHeader: String,
        @Path("package_name") package_name: String,
        @Part icon_preview: MultipartBody.Part,
    ) : Call<Any>

    @PUT("/current_user/do_not_disturb")
    fun setDoNotDisturb(
        @Part do_not_disturbe_mode: Boolean
    ) : Call<Profile>
}