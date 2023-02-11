package com.nux.studio.dvor.data.repository

import com.nux.studio.dvor.data.local.token.TokenDao
import com.nux.studio.dvor.data.local.token.TokenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val dao: TokenDao
) {

    fun addToken(token: String) {
        dao.addToken(token = TokenEntity(token = token))
    }

    fun getToken(): String {
        val response = dao.getToken()
        return response?.token ?: ""
    }

}