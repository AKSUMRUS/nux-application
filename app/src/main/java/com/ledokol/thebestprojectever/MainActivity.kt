package com.ledokol.thebestprojectever

import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestprojectever.services.MyReceiver
import com.ledokol.thebestprojectever.ui.navigation.StartNavigation
import com.ledokol.thebestprojectever.ui.theme.TheBestProjectEverTheme
import dagger.hilt.android.AndroidEntryPoint


//class MainViewModelFactory(val application: Application) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return MainViewModel(application) as T
//    }
//}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var intentService: Intent? = null
    private lateinit var myReceiver: MyReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        myReceiver = MyReceiver()

        if(bundle!=null){
//            val gamePackageName:String = bundle.getString("gamePackageName").toString()
//            if(gamePackageName!=null && gamePackageName!=""){
//                openAppRating(context = this, packageName = gamePackageName)
//            }
        }

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

    override fun onStop() {
        super.onStop()
//        unregisterReceiver(myReceiver)
    }
}


private const val flags = PackageManager.GET_META_DATA or
        PackageManager.GET_SHARED_LIBRARY_FILES or
        PackageManager.GET_UNINSTALLED_PACKAGES

fun openAppRating(
    context: Context,
    packageName: String
) {
    // you can also use BuildConfig.APPLICATION_ID
    val appId: String = context.getPackageName()
//    val rateIntent = Intent(
//        Intent.ACTION_VIEW,
//        Uri.parse("market://details?id=$appId")
//    )
    var marketFound = false

    // find all applications able to handle our rateIntent
    val otherApps: MutableList<ApplicationInfo> = context.getPackageManager().getInstalledApplications(flags)
    for (otherApp in otherApps) {
        // look for Google Play application
        if (otherApp.packageName
            == packageName
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