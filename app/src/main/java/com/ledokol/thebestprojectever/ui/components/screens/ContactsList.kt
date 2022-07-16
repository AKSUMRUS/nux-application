package com.ledokol.thebestprojectever.ui.components.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.presentation.ContactViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH1
import com.ledokol.thebestprojectever.ui.components.atoms.Search
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull
import com.ledokol.thebestprojectever.ui.components.molecules.ContactInList

private const val TAG = "getContacts"

class ContactClass(val name: String, val phone: String){

}

@SuppressLint("Range")
@Composable
fun ContactsList(
    navController: NavController,
    contactsViewModel: ContactViewModel,
    profileViewModel: ProfileViewModel,
) {

    val context: Context = LocalContext.current
    var textSearch by remember{ mutableStateOf("")}
    var showShareScreen by remember { mutableStateOf(false) }

    fun onClick(){
        showShareScreen = true
    }

    fun onValueChange(text: String){
        textSearch = text
//        contactsViewModel.getContacts(text)
    }

    if(!showShareScreen){
        Box(){
            LazyColumn(content = {
                item(){
                    Search(
                        text = textSearch,
                        placeholder = stringResource(id = R.string.choose_name),
                        onValueChange = {
                            onValueChange(it)
                        },
                        modifier = Modifier.padding(
                            top = 120.dp,
                        )
                    )
                }
                items(contactsViewModel.state.contacts!!){ contact ->
                    ContactInList(
                        name = contact.name,
                        navController = navController,
                        onClick = { onClick() },
                    )
                }
            },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                    )
            )

            ButtonPrimaryFull(
                text = "Дальше",
                onClick = {
                    profileViewModel.setFinishRegister(accessToken = profileViewModel.state.profile!!.access_token)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 20.dp)
            )
        }
    }else{
        navController.navigate("share_screen"){
            popUpTo("share_screen")
            launchSingleTop = true
        }
    }

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

                contactsViewModel.insertContact(Contact(contactId = id, name = name, phones = phones[0]))
                pCur!!.close()
            }
        }
    }

    if (cur != null) {
        cur.close()
    }
}