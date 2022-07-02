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
    val gamePackage: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon")
    val icon: Bitmap?
)