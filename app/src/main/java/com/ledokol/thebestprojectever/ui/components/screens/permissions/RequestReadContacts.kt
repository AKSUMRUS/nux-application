package com.ledokol.thebestprojectever.ui.components.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.*
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.ContactViewModel
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.texts.Body1
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestprojectever.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestprojectever.ui.components.atoms.LoadingView
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimaryFull


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestReadContacts(
    navController: NavController,
    gamesViewModel: GamesViewModel,
    contactsViewModel: ContactViewModel,
) {
    val context: Context = LocalContext.current
    var onClick by remember {
        mutableStateOf({})
    }

    var showContent by remember {
        mutableStateOf(false)
    }

    var permissionAlreadyRequested by remember {
        mutableStateOf(false)
    }

    val permissionState = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS) {
        permissionAlreadyRequested = true
    }

    if(permissionState.status.isGranted){
        getContactArray(
            context,
            contactsViewModel
        )
        contactsViewModel.getContacts("")

        navController.navigate("quick_game"){
            popUpTo("quick_game")
            launchSingleTop = true
        }
    }else if (!permissionAlreadyRequested && !permissionState.status.shouldShowRationale) {
        onClick = { permissionState.launchPermissionRequest() }
        showContent = true
    } else if (permissionState.status.shouldShowRationale) {
        onClick = { permissionState.launchPermissionRequest() }
        showContent = true
    } else {
        onClick = { context.openSettings() }
        showContent = true
    }

    if(showContent){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(top = 100.dp, bottom = 100.dp, start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,

            ){

            Column(
                modifier = Modifier.weight(3f)
            ){
                HeadlineH4(
                    text = stringResource(id = R.string.hello_permission_contacts),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.W500,
                )
                HeadlineH5(
                    text = stringResource(id = R.string.need_permission_contacts),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.W500,
                )

                HeadlineH4(
                    text = stringResource(id = R.string.reason_permission_contacts),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.W500,
                )
            }

            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Bottom

            ){
                Body1(
                    text = stringResource(id = R.string.explain_permission_contacts),
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    fontWeight = FontWeight.W500,
                )

                ButtonPrimaryFull(
                    text = stringResource(id = R.string.allow_permission_contacts),
                    onClick = onClick,
                )
            }
        }
    }else{
        LoadingView()
    }
}

fun Context.openSettings() {

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package",packageName,null)
    intent.data = uri
    startActivity(intent)
}

