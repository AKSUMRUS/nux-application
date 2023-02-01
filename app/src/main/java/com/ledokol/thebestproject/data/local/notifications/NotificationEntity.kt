package com.ledokol.thebestproject.data.local.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ledokol.thebestproject.data.local.user.User

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    @SerializedName("dt_sent")
    val dt_sent: String = "",
    @SerializedName("from_user")
    val from_user: User,
)
