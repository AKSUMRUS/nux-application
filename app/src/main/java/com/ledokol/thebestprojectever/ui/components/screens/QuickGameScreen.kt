package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ledokol.thebestprojectever.services.MyService
import com.ledokol.thebestprojectever.ui.components.atoms.*


@Composable
fun QuickGameScreen(){
    val serviceClass = MyService::class.java
    val context = LocalContext.current

    Log.d("START_QUICK_GAME","START_QUICK_GAME")
    val intent = Intent(context, serviceClass)

    context.startService(intent)

    Column(
    ) {
        Body1(text = "Quick game", modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
            type = "surface"
        )

    }
}

fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}