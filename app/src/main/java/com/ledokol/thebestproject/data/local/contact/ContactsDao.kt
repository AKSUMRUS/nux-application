package com.ledokol.thebestproject.data.local.contact

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<Contact>)

    @Query(
        """
            SELECT *
            FROM contacts
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
        """
    )
    fun getContacts(query: String): List<Contact>

    @Query("DELETE FROM contacts")
    fun clearContacts()

}