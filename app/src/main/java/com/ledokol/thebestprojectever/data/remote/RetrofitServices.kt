package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.profile.ProfileToken
import com.ledokol.thebestprojectever.data.local.user.Apps
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.domain.*
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

    @PUT("status/set/android")
    fun setStatus(
        @Header("Authorization") authHeader: String,
        @Body status: App
    )
            : Call<StatusJSON>

    @PUT("status/leave")
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
        @Body firebase_messaging_token: String
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
    ) : Call<FriendsInviteToGame>

    @GET("/get_me")
    fun getMe(
        @Header("Authorization") authHeader: String
    ) : Call<Profile>
}