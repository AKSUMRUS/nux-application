package com.ledokol.thebestprojectever.ui.components.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.data.local.contact.Contact
import com.ledokol.thebestprojectever.presentation.ContactViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel


private const val TAG = "getContacts"

// А ЭТО БЛЯТЬ ЧТО ТАКОЕ!?!?

class ContactClass(val name: String, val phone: String){

}

@SuppressLint("Range")
@Composable
fun ContactsList(
    navController: NavController,
    contactsViewModel: ContactViewModel,
    profileViewModel: ProfileViewModel,
) {

//    val context: Context = LocalContext.current
//    var textSearch by remember{ mutableStateOf("")}
//    var showShareScreen by remember { mutableStateOf(false) }
//
//    fun onClick(){
//        showShareScreen = true
//    }
//
//    fun onValueChange(text: String){
//        textSearch = text
////        contactsViewModel.getContacts(text)
//    }
//
//    if(!showShareScreen){
//        Box(){
//            LazyColumn(content = {
//                item(){
//                    Search(
//                        text = textSearch,
//                        placeholder = stringResource(id = R.string.choose_name),
//                        onValueChange = {
//                            onValueChange(it)
//                        },
//                        modifier = Modifier.padding(
//                            top = 120.dp,
//                        )
//                    )
//                }
//                items(contactsViewModel.state.contacts!!){ contact ->
//                    ContactInList(
//                        name = contact.name,
//                        navController = navController,
//                        onClick = { onClick() },
//                    )
//                }
//            },
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(
//                        start = 20.dp,
//                        end = 20.dp,
//                    )
//            )
//
//            ButtonPrimaryFull(
//                text = "Дальше",
//                onClick = {
//                    profileViewModel.onEvent(
//                        ProfileEvent.SetFinishRegister(accessToken = profileViewModel.state.profile!!.access_token)
//                    )
//                },
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(end = 20.dp, bottom = 20.dp)
//            )
//        }
//    }else{
//        navController.navigate("share_screen"){
//            popUpTo("share_screen")
//            launchSingleTop = true
//        }
//    }

}

@SuppressLint("Range")
fun getContactArray(
    context: Context,
    contactsViewModel: ContactViewModel,
) {
//    val mProjection = arrayOf(
//        ContactsContract.CommonDataKinds.Email._ID,
//        ContactsContract.CommonDataKinds.Email.ADDRESS,
//        ContactsContract.CommonDataKinds.Email.DISPLAY_NAME,
//        ContactsContract.CommonDataKinds.Email.HAS_PHONE_NUMBER,
//        ContactsContract.CommonDataKinds.Email.LABEL
//    )
//    val PROJECTION: Array<out String> = arrayOf(
//        ContactsContract.Data._ID,
//        ContactsContract.Data.MIMETYPE,
//        ContactsContract.Data.DATA1,
//        ContactsContract.Data.DATA2,
//        ContactsContract.Data.DATA3,
//        ContactsContract.Data.DATA4,
//        ContactsContract.Data.DATA5,
//        ContactsContract.Data.DATA6,
//        ContactsContract.Data.DATA7,
//        ContactsContract.Data.DATA8,
//        ContactsContract.Data.DATA9,
//        ContactsContract.Data.DATA10,
//        ContactsContract.Data.DATA11,
//        ContactsContract.Data.DATA12,
//        ContactsContract.Data.DATA13,
//        ContactsContract.Data.DATA14,
//        ContactsContract.Data.DATA15
//    )

    val cr: ContentResolver = context.contentResolver
    val cur: Cursor? = cr.query(
        ContactsContract.Contacts.CONTENT_URI,
        null, null, null, null
    )
    if ((if (cur != null) cur.getCount() else 0) > 0) {
        while (cur != null && cur.moveToNext()) {
            val id: String = cur.getString(
                cur.getColumnIndex(ContactsContract.Contacts._ID)
            )
            if(cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME
                )==null) continue
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