package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.user.Apps
import com.ledokol.thebestprojectever.data.local.user.CurrentApp
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.domain.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface
RetrofitServices {

    @FormUrlEncoded
    @POST("token")
    fun login(
        @Field("username") nickname: String,
        @Field("password") password: String )
    : Call<Profile>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun createProfile(@Body profile: ProfileJSON)
            : Call<Profile>

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

    )
            : Call<StatusJSON>

    @PUT("sync_installed_apps/android")
    fun shareGames(
        @Header("Authorization") authHeader: String,
        @Body games: AppsStatus
    )
            : Call<AppsStatus>

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
        @Field("friends_ids") friends_ids: List<String>,
        @Field("app_id") app_id: String,
    )
}