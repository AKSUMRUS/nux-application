package com.ledokol.thebestprojectever

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.screens.LoginScreen
import com.ledokol.thebestprojectever.ui.components.screens.MainScreen
import com.ledokol.thebestprojectever.ui.components.screens.TestScreen
import com.ledokol.thebestprojectever.ui.navigation.LoginAndSignUp
import com.ledokol.thebestprojectever.ui.navigation.StartNavigation
import com.ledokol.thebestprojectever.ui.theme.TheBestProjectEverTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheBestProjectEverTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    StartNavigation()
                }
            }
        }
    }
}