package com.nux.studio.dvor.ui.components.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nux.dvor.ui.components.molecules.ContactInList
import com.nux.studio.dvor.R
import com.nux.studio.dvor.core_ui.atoms.Search
import com.nux.studio.dvor.data.local.contact.Contact

@Composable
fun ShowMyContacts(
    textSearch: String,
    onValueChange: (String) -> Unit,
    contacts: List<Contact>?,
    inviteFriend: () -> Unit,
    addFriend: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
            )
    ) {
        Search(
            text = textSearch,
            placeholder = stringResource(id = R.string.enter_name_friend_from_contacts),
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier.padding(
                top = 120.dp,
            )
        )

        LazyColumn(
            content = {
                item {
                }
                items(contacts!!.filter {
                    it.name.lowercase().contains(textSearch.lowercase())
                }) { contact ->

                    ContactInList(
                        name = contact.name,
                        onClick = { if (contact.registered) addFriend(contact.phones) else inviteFriend() },
                        textButton = if (contact.registered) "Добавить в друзья" else "Пригласить",
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }

}