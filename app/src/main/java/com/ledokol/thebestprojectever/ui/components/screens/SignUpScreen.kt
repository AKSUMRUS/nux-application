package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.atoms.Button
import com.ledokol.thebestprojectever.ui.components.atoms.HeadlineH1
import com.ledokol.thebestprojectever.ui.components.atoms.TextButton
import com.ledokol.thebestprojectever.ui.components.atoms.TextField
import org.intellij.lang.annotations.JdkConstants


@Composable
fun SignUpScreen(navController: NavController,navController2: NavController){

    val pref : SharedPreferences = LocalContext.current.getSharedPreferences("Data",
        Context.MODE_PRIVATE
    )
    var editor: SharedPreferences.Editor = pref.edit()

    val (nickname,setNickname) = remember{ mutableStateOf("") }
    val (password,setPassword) = remember{ mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadlineH1(text = stringResource(R.string.login))
        TextField(
            label = stringResource(R.string.nickname),
            text = nickname,
            onValueChange = { setNickname(it) },
        )
        TextField(
            label = stringResource(R.string.password),
            text = password,
            onValueChange = { setPassword(it) },
        )
        Button(text = stringResource(R.string.login), onClick = {
            editor.putString("nickname",nickname)
            editor.putString("passwords",password)
            editor.putString("token","jsadjksadkjh")
            editor.apply()
            navController2.navigate("main") {
                popUpTo("main")
                launchSingleTop = true
            }
        })
        TextButton(text = stringResource(R.string.dont_have_an_account), onClick = {
            navController.navigate("login_screen") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        })
    }
}