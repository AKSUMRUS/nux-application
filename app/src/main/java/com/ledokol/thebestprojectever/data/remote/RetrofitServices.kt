package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.domain.ProfileJSON
import com.ledokol.thebestprojectever.domain.StatusJSON
import dagger.Binds
import dagger.internal.DaggerGenerated
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Singleton


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
    @GET("user/{nickname}")
    fun getUser(
        @Path("nickname") nickname: String
    )
            : Call<User>

    @PUT("status/set/android")
    fun setStatus(
        @Body status: StatusJSON
    )

    @PUT("status/leave")
    fun leaveStatus(

    )
}