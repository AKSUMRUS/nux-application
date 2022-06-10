package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.local.StockDatabase
import com.ledokol.thebestprojectever.data.mapper.toUser
import com.ledokol.thebestprojectever.domain.module.User
import com.ledokol.thebestprojectever.domain.repository.StockRepository
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockDatabase,
    val db: StockDatabase
): StockRepository {

    private val dao = db.dao

    override suspend fun getUser(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<User>> {
        return flow{
            emit(Resource.Loading(true))
            val localUser = dao.searchUser()
            emit(Resource.Success(
                data = localUser.toUser()
            ))

            val isDBEmpty = localUser.isLogined
            val shouldLoadFromCache = !isDBEmpty && !fetchFromRemote
            if(shouldLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error("You are not logged in"))
        }
    }

}