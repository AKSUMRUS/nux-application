package com.ledokol.thebestprojectever.data.local.notifications

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    @SerializedName("foo")
    val foo : String,
    @SerializedName("bar")
    val bar : Int
)
