package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.domain.ProfileJSON
import dagger.Binds
import dagger.internal.DaggerGenerated
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Singleton


interface RetrofitServices {

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

    @Headers("Content-Type: application/json")
    @GET("friend")
    fun getFriends()
            : Call<List<User>>
}