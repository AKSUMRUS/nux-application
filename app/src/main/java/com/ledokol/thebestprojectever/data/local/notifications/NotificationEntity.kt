package com.ledokol.thebestprojectever.data.local.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ledokol.thebestprojectever.data.local.user.User

@Entity(tableName = "notifications")
data class NotificationEntity(
    @SerializedName("dt_sent")
    val dt_sent : String = "",
    @PrimaryKey
    @SerializedName("from_user")
    val from_user : User,
)