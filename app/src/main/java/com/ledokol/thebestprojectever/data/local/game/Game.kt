package com.ledokol.thebestprojectever.data.local.game

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "package")
    val android_package_name: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "icon_preview")
    val icon_preview: String?,
    @ColumnInfo(name = "image_wide")
    val image_wide: String?,
    @ColumnInfo(name = "icon_large")
    val icon_large: String?
)