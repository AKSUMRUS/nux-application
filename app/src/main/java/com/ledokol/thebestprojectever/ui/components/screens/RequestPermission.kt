package com.ledokol.thebestprojectever.ui.components.screens

import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process.myUid
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission() {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val checkPermission = remember{mutableStateOf(false)}
//    val usagePermissionState = rememberPermissionState(android.Manifest.permission.PACKAGE_USAGE_STATS)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                checkPermission.value = checkForPermission(context)
            }else if (event == Lifecycle.Event.ON_RESUME) {
                checkPermission.value = checkForPermission(context)
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

//        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(

    ){
        if (!checkPermission.value) {
            Text("Не получен доступ!")
            Button(
                onClick = {
                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }
            ){
                Text("Получить разрешение")
            }
        }else{
            Text("Доступ получен!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        }
    }
//    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.ACTION_USAGE_ACCESS_SETTINGS)
//    if (cameraPermissionState.status.isGranted) {
//        Text("Camera permission Granted")
//    } else {
//        Column {
//            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
//                "The camera is important for this app. Please grant the permission."
//            } else {
//                "Camera not available"
//            }
//
//            Text(textToShow)
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
//                Text("Request permission")
//            }
//        }
//    }
}

private fun checkForPermission(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, myUid(), context.packageName)
    return mode == MODE_ALLOWED
}
