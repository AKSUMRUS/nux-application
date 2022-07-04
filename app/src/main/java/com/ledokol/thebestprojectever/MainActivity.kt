package com.ledokol.thebestprojectever

import android.app.ActivityManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.wrappers.InstantApps
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.screens.TestScreen
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentService = Intent(this, MyService::class.java)
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startService(intentService)

        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//            param(FirebaseAnalytics.Param.SCREEN_NAME, "Open app")
//            param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
//        }

        setContent {
            TheBestProjectEverTheme {

//                val owner = LocalViewModelStoreOwner.current

                val navController = rememberNavController()
//
//                owner?.let {
//                    val viewModel: MainViewModel = viewModel(
//                        it,
//                        "MainViewModel",
//                        MainViewModelFactory(
//                            LocalContext.current.applicationContext
//                                    as Application
//                        )
//                    )
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        StartNavigation(
                            navController = navController,
                            analytics = mFirebaseAnalytics
                        )
//                        TestScreen()
                    }
//                }
            }
        }
    }
}