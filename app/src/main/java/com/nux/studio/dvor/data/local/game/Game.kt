package com.nux.studio.dvor.data.local.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "package")
    val android_package_name: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "icon_preview")
    val icon_preview: String?,
    @ColumnInfo(name = "image_wide")
    val image_wide: String?,
    @ColumnInfo(name = "icon_large")
    val icon_large: String?,
    @ColumnInfo(name = "installed")
    val installed: Boolean? = false,
    @ColumnInfo(name = "activity_last_two_weeks")
    val activity_last_two_weeks: Int? = 0,
    @ColumnInfo(name = "activity_total")
    val activity_total: Int? = 0,
    @ColumnInfo(name = "dt_last_activity")
    val dt_last_activity: String? = "",

    )