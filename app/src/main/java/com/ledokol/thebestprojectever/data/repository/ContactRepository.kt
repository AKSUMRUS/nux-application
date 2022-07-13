package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.data.local.contact.ContactsDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor(
    private val api: RetrofitServices,
    private val dao: ContactsDao,
){

    fun insertContacts(contacts: List<Contact>){
        dao.insertContacts(contacts)
    }

    fun insertContact(contact: Contact){
        dao.insertContact(contact)
    }

    fun clearContacts(){
        dao.clearContacts()
    }


}