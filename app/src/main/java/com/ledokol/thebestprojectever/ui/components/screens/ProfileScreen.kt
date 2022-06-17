package com.ledokol.thebestprojectever.ui.components.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.molecules.UserGames

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun ProfileScreen(
    viewModel: MainViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
    ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.anonymous),
                    contentDescription = "Аватар",
                    tint = Color.Unspecified,
                )

                Body1(
                    text = "Имя пользователя",
                )
                Button(text = "Quit",
                    onClick = { viewModel.clearProfile() },
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                )
            }

            Column() {
                UserGames()
            }
    }
}