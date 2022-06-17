package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.profile.Profile
import com.ledokol.thebestprojectever.domain.ProfileJSON
import retrofit2.Call
import retrofit2.http.*

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

}