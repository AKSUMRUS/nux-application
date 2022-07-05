package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.Body1
import com.ledokol.thebestprojectever.ui.components.molecules.GameInQuickGames
import com.ledokol.thebestprojectever.ui.components.molecules.ScreenTitle


@Composable
fun QuickGameScreen(
    viewModel: GamesViewModel
){
    val TAG = "FIREBASE MESSAGING"
    val games = viewModel.state.games
    var token by remember{ mutableStateOf("")}
    val context: Context = LocalContext.current

    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(TAG, "Fetching FCM registration token failed", task.exception)
            return@OnCompleteListener
        }

        // Get new FCM registration token
        val tokenGet = task.result

        token = tokenGet
        Log.w(TAG, token)
        Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
    ) {
        Body1(text = token)

        games?.let { GridGames(it) }
    }
}

@Composable
fun GridGames(games: List<Game>) {
    val context = LocalContext.current

    var myClipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 120.dp, start = 20.dp, end = 20.dp),
        modifier = Modifier,
    ) {
        item (span = { GridItemSpan(2) },
        ){
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                ScreenTitle(name = stringResource(id = R.string.nav_quick_game), description = stringResource(id = R.string.description_quick_game))
                com.ledokol.thebestprojectever.ui.components.atoms.Button(
                    text = "Копировать",
                    onClick = {
                        myClipboard.setPrimaryClip(ClipData.newPlainText("simple text", text))
                    })
            }
        }

        items(games) { game ->
            GameInQuickGames(
                packageName = "fdfdfd",
                icon = getIcon(
                    context = context,
                    packageManager = context.packageManager,
                    packageName = game.gamePackage,
                )!!.asImageBitmap(),
                backgroundImage = ImageBitmap.imageResource(id = R.drawable.anonymous)
            )
        }
    }
}


fun log(message: String) {
    Log.d("BACKGROUND_SERVICE", message)
}