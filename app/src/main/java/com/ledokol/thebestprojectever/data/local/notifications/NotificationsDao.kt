package com.ledokol.thebestprojectever.data.local.notifications

import androidx.room.*

@Dao
interface NotificationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications")
    fun getNotifications() : List<NotificationEntity>

    @Query("DELETE FROM notifications")
    fun clearNotifications()

}