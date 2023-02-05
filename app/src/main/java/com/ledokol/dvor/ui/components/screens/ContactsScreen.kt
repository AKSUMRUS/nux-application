package com.ledokol.dvor.ui.components.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ledokol.dvor.data.local.contact.Contact
import com.ledokol.dvor.data.local.user.UserEvent
import com.ledokol.dvor.presentation.ContactViewModel
import com.ledokol.dvor.presentation.ProfileViewModel
import com.ledokol.dvor.presentation.UserViewModel
import com.ledokol.dvor.ui.components.molecules.BackToolbar
import com.ledokol.dvor.ui.components.molecules.LoadingViewCenter
import com.ledokol.dvor.ui.components.molecules.ShowMyContacts
import com.ledokol.dvor.ui.components.molecules.contacts.RequestReadContacts
import com.ledokol.dvor.ui.components.screens.profile.getLinkProfile


private const val TAG = "getContacts"

// А ЭТО БЛЯТЬ ЧТО ТАКОЕ!?!?

class ContactClass(val name: String, val phone: String)

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("Range")
@Composable
fun ContactsScreen(
    navController: NavController,
    contactsViewModel: ContactViewModel,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel,
) {

    val context: Context = LocalContext.current
    var textSearch by remember { mutableStateOf("") }
    var showShareScreen by remember { mutableStateOf(false) }

    var onClickButtonRequest by remember {
        mutableStateOf({})
    }

    var permissionAlreadyRequested by remember {
        mutableStateOf(false)
    }

    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS) {
        permissionAlreadyRequested = true
    }

    LaunchedEffect(key1 = true, block = {
        contactsViewModel.clearContacts()
    })

    if (permissionState.status.isGranted && contactsViewModel.state.contacts!!.isEmpty()) {
        getContactArray(
            context,
            contactsViewModel
        )
        contactsViewModel.getContacts("")
    } else if (!permissionAlreadyRequested && !permissionState.status.shouldShowRationale) {
        onClickButtonRequest = { permissionState.launchPermissionRequest() }
    } else if (permissionState.status.shouldShowRationale) {
        onClickButtonRequest = { permissionState.launchPermissionRequest() }
    } else {
        onClickButtonRequest = { context.openSettings() }
    }

    fun inviteFriend() {
        val dynamicLink = getLinkProfile(profile_id = profileViewModel.state.profile!!.id)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Добавляй меня в друзья в приложении Dvor $dynamicLink")
        intent.type = "text/plain"

        context.startActivity(Intent.createChooser(intent, "Поделиться"))
    }

    fun addFriend(phone: String) {
        userViewModel.onEvent(
            UserEvent.AddFriend(
                phone = phone,
                access_token = profileViewModel.state.profile!!.access_token
            )
        )
    }

    fun onValueChange(text: String) {
        textSearch = text
//        contactsViewModel.getContacts(text)
    }

    if (permissionState.status.isGranted) {
        if (contactsViewModel.state.isLoading) {
            LoadingViewCenter()
        } else {
            Box {
                ShowMyContacts(
                    textSearch = textSearch,
                    onValueChange = { onValueChange(it) },
                    contacts = contactsViewModel.state.contacts,
                    inviteFriend = {
                        inviteFriend()
                    },
                    addFriend = {
                        addFriend(it)
                    }
                )

                BackToolbar(
                    buttonBackClick = {
                        navController.popBackStack()
                    }
                )
//            ButtonFull(
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
            }
        }
    } else {
        RequestReadContacts(
            onClickButton = {
                onClickButtonRequest()
            }
        )
    }

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
    if ((cur?.count ?: 0) > 0) {
        while (cur != null && cur.moveToNext()) {
            val id: String = cur.getString(
                cur.getColumnIndex(ContactsContract.Contacts._ID)
            )
            if (cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME
                ) == null
            ) continue
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
                    val phoneNo: String = pCur.getString(
                        pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                    )
                    phones.add(phoneNo)

                    Log.i(TAG, "Name: $name")
                    Log.i(TAG, "Phone Number: $phoneNo")
                }

                if (phones[0][0] == '8') {
                    phones[0] = "+7" + phones[0].subSequence(1, phones[0].length)
                }
                contactsViewModel.insertContact(
                    Contact(
                        contactId = id,
                        name = name,
                        phones = phones[0]
                    )
                )
                pCur.close()
            }
        }
    }

    cur?.close()
}

fun Context.openSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
