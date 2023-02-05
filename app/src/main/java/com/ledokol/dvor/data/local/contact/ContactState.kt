package com.ledokol.dvor.data.local.contact

data class ContactState(
    var contacts: List<Contact>? = mutableListOf(),
    var clickedContacts: List<Contact>? = mutableListOf(),
    var isLoading: Boolean = true,
    var searchQuery: String = "",
)