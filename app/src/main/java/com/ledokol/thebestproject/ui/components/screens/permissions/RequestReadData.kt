package com.ledokol.thebestproject.ui.components.screens.permissions

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonFull
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH3

@Composable
fun RequestReadData(
    navController: NavController,
    gamesViewModel: GamesViewModel,
    profileViewModel: ProfileViewModel,
    userViewModel: UserViewModel
) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    var checkPermission by remember{ mutableStateOf(true) }

    DisposableEffect(lifecycleOwner, checkPermission) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START || event == Lifecycle.Event.ON_RESUME) {

                if(checkPermissionReadData(context)){

                    profileViewModel.onEvent(ProfileEvent.SetFinishRegister(accessToken = profileViewModel.state.profile!!.access_token))
                }else{
                    checkPermission = false
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if(!checkPermission){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(top = 100.dp, bottom = 100.dp,start = 30.dp, end = 30.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,

            ){

            Column(
                modifier = Modifier.weight(3f)
            ){
                HeadlineH5(text = stringResource(id = R.string.hello_permission_data),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    fontWeight = FontWeight.W500,
                )
                HeadlineH5(text = stringResource(id = R.string.need_permission_data),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    fontWeight = FontWeight.W500,
                )

                HeadlineH3(text = stringResource(id = R.string.reason_permission_data),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    fontWeight = FontWeight.W500,
                )
            }

            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.Bottom

            ){
                Body1(text = stringResource(id = R.string.explain_permission_data),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                    ,
                    fontWeight = FontWeight.W500,
                )

                ButtonFull(
                    text = stringResource(id = R.string.allow_permission_data),
                    colorText = MaterialTheme.colors.onBackground,
                    onClick = {
                        context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                )
            }

        }
    }
}

fun checkPermissionReadData(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
    return mode == AppOpsManager.MODE_ALLOWED
}

