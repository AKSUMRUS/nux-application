package com.ledokol.thebestproject

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestproject.data.local.user.UserEvent
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.services.MyReceiver
import com.ledokol.thebestproject.ui.navigation.ScreenRoutes
import com.ledokol.thebestproject.ui.navigation.StartNavigation
import com.ledokol.thebestproject.ui.theme.TheBestProjectEverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var myReceiver: MyReceiver
    val userViewModel: UserViewModel by viewModels<UserViewModel>()
    val profileViewModel: ProfileViewModel by viewModels<ProfileViewModel>()
    val TAG = "startMainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myReceiver = MyReceiver()
        val bundle = intent.extras
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        userViewModel.state = userViewModel.state.copy(
            openScreen = null
        )

        if(bundle!=null&& bundle.containsKey("notification_id")){
            Log.e(TAG, bundle.toString())
            val notification_id = bundle.getString("notification_id").toString()
            val notification_type = bundle.getString("notification_type").toString()

            if(notification_type == "friends_invite"){
                val profile_id = bundle.getString("userId_$notification_id").toString()
                Log.e(TAG, profile_id.toString())
                userViewModel.onEvent(UserEvent.GetFriendUser(profile_id.toString()))
                userViewModel.onEvent(UserEvent.OpenScreen(screen = ScreenRoutes.PREVIEW_FRIEND))
            }else{
                val gamePackageName:String = bundle.getString("gamePackageName_$notification_id").toString()
                bundle.clear()
                if(gamePackageName!=null && gamePackageName!=""){
                    openAppRating(context = this, packageName = gamePackageName)
                }
            }
        }

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                if (deepLink != null &&
                    deepLink.getBooleanQueryParameter("profile_id", false)) {
                        val profile_id = deepLink.getQueryParameter("profile_id")
                        Log.e(TAG, profile_id.toString())
                        userViewModel.onEvent(UserEvent.GetFriendUser(profile_id.toString()))
                        userViewModel.onEvent(UserEvent.OpenScreen(screen = ScreenRoutes.PREVIEW_FRIEND))
                    }
            }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(com.ledokol.thebestproject.ui.components.screens.games.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val tokenGet = task.result

            if(profileViewModel.state.profile != null){
                Log.e("myFirebaseToken", "$tokenGet ${profileViewModel.state.profile!!.access_token}")
                profileViewModel.setCurrentFirebaseToken(tokenGet, profileViewModel.state.profile!!.access_token)
            }
        })


        FirebaseApp.initializeApp(this@MainActivity)

        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mFirebaseAnalytics.logEvent("start_app") {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "open_app")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }
        setContent {
            val navController = rememberNavController()

            TheBestProjectEverTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        StartNavigation(
                            navController = navController,
                            analytics = mFirebaseAnalytics
                        )
                    }
//                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED)
        registerReceiver(myReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myReceiver)
    }
}


private const val flags = PackageManager.GET_META_DATA or
        PackageManager.GET_SHARED_LIBRARY_FILES or
        PackageManager.GET_UNINSTALLED_PACKAGES

fun openAppRating(
    context: Context,
    packageName: String
) {
    var marketFound = false

    // find all applications able to handle our rateIntent
    val otherApps: MutableList<ApplicationInfo> = context.packageManager.getInstalledApplications(flags)
    for (otherApp in otherApps) {
        // look for Google Play application
        if (
            otherApp.packageName == packageName
        ) {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
            launchIntent?.let { context.startActivity(it) }
            marketFound = true
            break
        }
    }

    // if GP not present on device, open web browser
    if (!marketFound) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
        }
    }
}