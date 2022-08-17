package com.ledokol.thebestprojectever.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.data.local.contact.ContactState
import com.ledokol.thebestprojectever.data.repository.ContactsRepository
import com.ledokol.thebestprojectever.data.repository.UsersRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactsRepository,
    private val usersRepository: UsersRepository,
): ViewModel() {
    var state by mutableStateOf(ContactState())


    fun insertContacts(contacts: List<Contact>){
        viewModelScope.launch {
            repository.insertContacts(contacts)
        }
    }

    fun insertContact(contact: Contact){
        viewModelScope.launch {
            usersRepository.checkExistsPhone(contact.phones).collect{
                result ->
                when(result) {
                    is Resource.Success -> {
                        contact.registered = result.data!!.exists
                        if(contact.registered){
                            Log.e("REGISTERED_CONTACT","EEEEEEEEEE")
                        }
                        repository.insertContact(contact)
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    fun clearContacts(){
        viewModelScope.launch {
            repository.clearContacts()
        }
    }

    fun getContacts(query: String){
        val TAG = "getContactsViewModel"
            viewModelScope.launch {
            repository.getContacts(query).collect{
                result ->
                when(result) {
                    is Resource.Success -> {
                        Log.e(TAG, result.data.toString())
                        result.data.let { contacts ->
                            state = state.copy(
                                contacts = contacts
                            )
                        }
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }

        }
    }

}