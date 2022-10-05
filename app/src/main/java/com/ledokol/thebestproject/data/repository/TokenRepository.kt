package com.ledokol.thebestproject.data.repository

import com.ledokol.thebestproject.data.local.token.TokenDao
import com.ledokol.thebestproject.data.local.token.TokenEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val dao : TokenDao
) {

    fun addToken(token : String){
        dao.addToken(token = TokenEntity(token = token))
    }

    fun getToken() : String {
        val response = dao.getToken()
        return response?.token ?: ""
    }

}