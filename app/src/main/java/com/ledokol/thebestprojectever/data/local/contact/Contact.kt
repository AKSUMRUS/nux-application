package com.ledokol.thebestprojectever.data.local.contact

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey
    @ColumnInfo(name = "contactId")
    var contactId: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "phones")
    var phones: String,
    @ColumnInfo(name = "registered")
    var registered: Boolean = false,
)