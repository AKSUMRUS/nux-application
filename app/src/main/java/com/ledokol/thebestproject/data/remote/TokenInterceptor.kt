package com.ledokol.thebestproject.data.remote

import com.ledokol.thebestproject.data.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val tokenRepository : TokenRepository
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token  = tokenRepository.getToken()

        val request = chain.request().newBuilder()
            .addHeader("Authorization","Bearer $token")
            .build()

        return chain.proceed(request)

    }

}