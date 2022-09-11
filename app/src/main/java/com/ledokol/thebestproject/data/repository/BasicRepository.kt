package com.ledokol.thebestproject.data.repository

import com.ledokol.thebestproject.data.error.ErrorCatcher
import com.ledokol.thebestproject.data.error.ErrorRemote
import com.ledokol.thebestproject.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class BasicRepository() {

    open fun <T,M> doSafeWork(
        doAsync: suspend () -> Response<M>,
        doOnSuccess: suspend (Response<M>) -> Unit = {},
        getResult: suspend (Response<M>) -> T?
    ) : Flow<Resource<T>> {
        return flow {
            emit(Resource.Loading(true))

            val response = try{
                doAsync()
            }
            catch (e : Exception){
                emit(Resource.Loading(false))

                emit(Resource.Error(message = ErrorRemote.NoInternet))

                return@flow
            }

            if(response.isSuccessful){
                doOnSuccess(response)
                response.body()?.let {
                    emit(Resource.Success(data = getResult(response)))
                }
            }
            else{
                emit(Resource.Error(message = ErrorCatcher.catch(response.code())))
            }

            emit(Resource.Loading(false))

        }
    }

}