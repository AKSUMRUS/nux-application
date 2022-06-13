package com.ledokol.thebestprojectever.data.remote

object Common {
    val BASE_URL = "http://0.0.0.0:8080/"
    val retrofitService: RetrofitServices
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}