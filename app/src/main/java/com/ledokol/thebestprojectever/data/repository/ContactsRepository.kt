package com.ledokol.thebestprojectever.data.repository

import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.data.local.contact.ContactsDao
import com.ledokol.thebestprojectever.data.remote.RetrofitServices
import com.ledokol.thebestprojectever.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(
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

    fun getContacts(
        query: String,
    ): Flow<Resource<List<Contact>>> {
        return flow{
            emit(Resource.Loading(true))

            val contacts = try{
                dao.getContacts(query)
            }catch(e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Error"))
                null
            }

            emit(Resource.Success(
                data = contacts
            ))

            emit(Resource.Loading(false))
        }
    }


}