package com.ledokol.dvor.data.repository

import com.ledokol.dvor.data.error.ErrorRemote
import com.ledokol.dvor.data.local.notifications.NotificationEntity
import com.ledokol.dvor.data.local.notifications.NotificationsDao
import com.ledokol.dvor.data.remote.RetrofitServices
import com.ledokol.dvor.domain.users.AddFriend
import com.ledokol.dvor.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepository @Inject constructor(
    val dao: NotificationsDao,
    private val api: RetrofitServices
) : BasicRepository() {

    fun getFriendRequestNotifications(token: String)
            : Flow<Resource<List<NotificationEntity>>> {
//        return flow{
//            emit(Resource.Loading(true))
//
//            val notificationsCall = try{
//                api.getFriendPendingInvites(authHeader = "Bearer $token").awaitResponse()
//            }
//            catch (e : Exception){
//                emit(Resource.Loading(false))
//                emit(Resource.Error(ErrorRemote.NoInternet))
//                return@flow
//            }
//
//            Log.e("NotificationsRepository", "notificationsCall: ${notificationsCall.body()}")
//
//            emit(Resource.Loading(false))
//
//            if (notificationsCall.isSuccessful){
//                val notifications = notificationsCall.body() ?: emptyList()
//                dao.clearNotifications()
//                dao.insertNotifications(notifications)
//
//                emit(Resource.Success(dao.getNotifications()))
//            }
//
//        }
        return doSafeWork(
            doAsync = {
                api.getFriendPendingInvites().awaitResponse()
            },
            doOnSuccess = { notificationsCall ->
                val notifications = notificationsCall.body() ?: emptyList()
                dao.clearNotifications()
                dao.insertNotifications(notifications)
            },
            getResult = {
                dao.getNotifications()
            }
        )
    }

    fun addFriend(
        notificationEntity: NotificationEntity
    ): Flow<Resource<List<NotificationEntity>>> {
        return flow {
            emit(Resource.Loading(true))

            val notificationsCall = try {
                api.addFriend(addFriend = AddFriend(user_id = notificationEntity.from_user.id))
                    .awaitResponse()
            } catch (e: Exception) {
                emit(Resource.Loading(false))
                emit(Resource.Error(ErrorRemote.NoInternet))
                return@flow
            }

            emit(Resource.Loading(false))

            if (notificationsCall.isSuccessful) {
                dao.deleteNotification(notificationEntity)

                emit(Resource.Success(dao.getNotifications()))
            }
        }
    }

    fun clearNotifications() {
        dao.clearNotifications()
    }

}