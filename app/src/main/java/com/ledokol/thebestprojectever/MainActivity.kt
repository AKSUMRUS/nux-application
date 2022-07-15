package com.ledokol.thebestprojectever

import android.content.Intent
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
import com.ledokol.thebestprojectever.services.MyService
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras

        if(bundle!=null){
            val gamePackageName:String = bundle.getString("gamePackageName").toString()
            val launchIntent = packageManager.getLaunchIntentForPackage(gamePackageName)
            Log.d("SAVEDINSTANCESTATE", gamePackageName)
            launchIntent?.let { startActivity(it) }
        }

        FirebaseApp.initializeApp(this@MainActivity)
        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "open_app")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        }


        setContent {
            TheBestProjectEverTheme {
                val navController = rememberNavController()
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