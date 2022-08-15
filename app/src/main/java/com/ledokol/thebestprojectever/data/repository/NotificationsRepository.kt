package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.local.notifications.NotificationEntity
import com.ledokol.thebestprojectever.data.local.notifications.NotificationsDao
import com.ledokol.thebestprojectever.data.local.user.User
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.domain.users.AddFriend
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepository @Inject constructor(
    val dao : NotificationsDao,
    val api : RetrofitServices
) {

    fun getFriendRequestNotifications(token : String)
    : Flow<Resource<List<NotificationEntity>>> {
        return flow{
            emit(Resource.Loading(true))

            val notificationsCall = try{
                api.getFriendPendingInvites(authHeader = "Bearer $token").awaitResponse()
            }
            catch (e : Exception){
                emit(Resource.Loading(false))
                emit(Resource.Error(e.message ?: "Error"))
                return@flow
            }

            emit(Resource.Loading(false))

            if (notificationsCall.isSuccessful){
                val notifications = notificationsCall.body() ?: emptyList()
                dao.insertNotifications(notifications)

                emit(Resource.Success(dao.getNotifications()))
            }

        }
    }

    fun addFriend(
        token : String,
        notificationEntity: NotificationEntity
    ) : Flow<Resource<List<NotificationEntity>>> {
        return flow {
            emit(Resource.Loading(true))

            val notificationsCall = try {
                api.addFriend(authHeader = "Bearer $token", addFriend = AddFriend(user_id = notificationEntity.from_user.id)).awaitResponse()
            } catch (e: Exception) {
                emit(Resource.Loading(false))
                emit(Resource.Error(e.message ?: "Error"))
                return@flow
            }

            emit(Resource.Loading(false))

            if (notificationsCall.isSuccessful) {
                dao.deleteNotification(notificationEntity)

                emit(Resource.Success(dao.getNotifications()))
            }
        }
    }

    fun clearNotifications(){
        dao.clearNotifications()
    }

}