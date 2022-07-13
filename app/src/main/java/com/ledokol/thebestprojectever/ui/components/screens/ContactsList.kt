package com.ledokol.thebestprojectever.ui.components.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.presentation.ContactViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH1
import com.ledokol.thebestprojectever.ui.components.molecules.ContactInList

val TAG = "getContacts"

class ContactClass(val name: String, val phone: String){

}

@SuppressLint("Range")
@Composable
fun ContactsList(
    navController: NavController,
    contactsViewModel: ContactViewModel,
) {

    val context: Context = LocalContext.current

    getContactArray(
        context,
        contactsViewModel
    )

    HeadlineH1(text = "fdklflkjfd")
    LazyColumn(content = {
        items(contactsViewModel.state.contacts!!){ contact ->
            ContactInList(
                name = contact.name,
                navController = navController,
            )
        }
    },
        modifier = Modifier.padding(20.dp)
    )

}

@SuppressLint("Range")
fun getContactArray(
    context: Context,
    contactsViewModel: ContactViewModel,
) {
    val cr: ContentResolver = context.contentResolver
    val cur: Cursor? = cr.query(
        ContactsContract.Contacts.CONTENT_URI,
        null, null, null, null
    )
    val array = mutableListOf<ContactClass>()

    if ((if (cur != null) cur.getCount() else 0) > 0) {
        while (cur != null && cur.moveToNext()) {
            val id: String = cur.getString(
                cur.getColumnIndex(ContactsContract.Contacts._ID)
            )
            val name: String = cur.getString(
                cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME
                )
            )
            if (cur.getInt(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                    )
                ) > 0
            ) {
                val pCur: Cursor? = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                )

//                array.add(ContactClass(name,""))
                val phones: MutableList<String> = mutableListOf()

                while (pCur!!.moveToNext()) {
                    val phoneNo: String = pCur!!.getString(
                        pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                    )
                    phones.add(phoneNo)
                    
                    Log.i(TAG, "Name: $name")
                    Log.i(TAG, "Phone Number: $phoneNo")
                }

                contactsViewModel.insertContact(Contact(contactId = 22,name = name, phones = phones[0]))
                pCur!!.close()
            }
        }
    }

    if (cur != null) {
        cur.close()
    }
}