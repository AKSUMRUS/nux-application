package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.local.notifications.NotificationEntity
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepository @Inject constructor(
    val dao : NotificationsRepository,
    val api : RetrofitServices
) {

    fun getNotifications()
    : Flow<Resource<List<NotificationEntity>>> {
        return flow{
            emit(Resource.Loading(true))
        }
    }

    fun clearNotifications(){
        dao.clearNotifications()
    }

}