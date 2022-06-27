package com.ledokol.thebestprojectever.data.local.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "package")
    val gamePackage: String,
    @ColumnInfo(name = "name")
    val name: String
)