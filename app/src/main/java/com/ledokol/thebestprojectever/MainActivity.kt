package com.ledokol.thebestprojectever

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.navigation.StartNavigation
import com.ledokol.thebestprojectever.ui.theme.TheBestProjectEverTheme


class MainViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, MyService::class.java))

        setContent {
            TheBestProjectEverTheme {

//                setStatusBarTransparent()
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: MainViewModel = viewModel(
                        it,
                        "MainViewModel",
                        MainViewModelFactory(
                            LocalContext.current.applicationContext
                                    as Application
                        )
                    )
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        StartNavigation(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun setStatusBarTransparent(){
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

//    WindowCompat.setDecorFitsSystemWindows(window, false)
//    window.setFlags(
//        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//    )

}