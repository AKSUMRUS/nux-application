package com.ledokol.thebestprojectever.data.remote

object Common {
    val BASE_URL = "http://10.0.2.2:8080/"
    val retrofitService: RetrofitServices
    get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}