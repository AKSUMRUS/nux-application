package com.ledokol.thebestprojectever.data.remote

import com.ledokol.thebestprojectever.data.local.Profile
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*

interface RetrofitServices {

    @FormUrlEncoded
    @POST("token")
    fun createProfile(
        @Field("username") nickname: String,
        @Field("password") password: String )
    : Call<Profile>
}