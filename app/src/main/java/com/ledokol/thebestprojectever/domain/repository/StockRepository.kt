package com.ledokol.thebestprojectever.domain.repository

import androidx.room.Query
import com.ledokol.thebestprojectever.domain.module.User
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getUser(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<User>>
}