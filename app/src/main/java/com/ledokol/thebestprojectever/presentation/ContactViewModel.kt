package com.ledokol.thebestprojectever.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.data.local.contact.ContactState
import com.ledokol.thebestprojectever.data.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val repository: ContactRepository,
): ViewModel() {
    var state by mutableStateOf(ContactState())


    fun insertContacts(contacts: List<Contact>){
        viewModelScope.launch {
            repository.insertContacts(contacts)
        }
    }

    fun insertContact(contact: Contact){
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun clearContacts(){
        viewModelScope.launch {
            repository.clearContacts()
        }
    }

}