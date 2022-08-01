package com.ledokol.thebestprojectever.ui.components.screens.profile

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.NonNull
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.profile.ProfileEvent
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.StatusViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.alertdialogs.AlertDialogShow
import com.ledokol.thebestprojectever.ui.components.molecules.GameInList
import com.ledokol.thebestprojectever.ui.components.molecules.profile.DisturbButton
import com.ledokol.thebestprojectever.ui.components.molecules.profile.ProfileTopBlock



// Гордей, ПОЧЕМУ ЭТО БЛЯТЬ ЗДЕСЬ!?!?!?!?!?!!??!
class GameProfile(private val packageName: String, val name: String = "Name", val icon: String = "Icon", val users: List<String> = listOf()){

    fun getName(context: Context, packageManager: PackageManager): String{
        return packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0)).toString()
    }

    fun getIcon(context: Context, packageManager: PackageManager): ImageBitmap {
        return (packageManager.getApplicationIcon(packageName) as BitmapDrawable).bitmap.asImageBitmap()
    }
}

fun getIcon(context: Context, packageManager: PackageManager, packageName: String): Bitmap? {
    val icon: Drawable = (packageManager.getApplicationIcon(packageName))
//    if (icon == null)
//        icon = getBitmapFromDrawable(context.getApplicationInfo().loadIcon(context.getPackageManager()));
    return getBitmapFromDrawable(icon)
}

@NonNull
private fun getBitmapFromDrawable(@NonNull drawable: Drawable): Bitmap? {
    val bmp: Bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bmp)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bmp
}


@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    gamesViewModel: GamesViewModel,
    statusViewModel: StatusViewModel
){

    val context = LocalContext.current
    val games = gamesViewModel.state.games
    val profile = profileViewModel.state.profile
    var openDialog by remember{ mutableStateOf(false) }
    var selectedGame by remember {
        mutableStateOf("")
    }

    fun onClickDisturb(){
        Log.d("ProfileScreen", "onClickDisturb ${profile?.access_token} ${profile!!.do_not_disturb}")
        profileViewModel.onEvent(ProfileEvent.SetDoNotDisturb(
            canDisturb = !profile.do_not_disturb,
            accessToken = profile.access_token
        ))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 0.dp, start = 0.dp, end = 0.dp),
            modifier = Modifier,
        ) {
            item(
                span = { GridItemSpan(2) }
            ) {
                Column(

                ) {
                    ProfileTopBlock(
                        profileViewModel = profileViewModel,
                        statusViewModel = statusViewModel,
                        navController = navController
                    )
                }
            }

            if (games != null) {
                items(games) { game ->
                    GameInList(
                        packageName = game.android_package_name,
                        name = game.name,
//                    Временно!
                        icon = game.icon_preview!!,
                        iconLarge = game.icon_large!!,
                        backgroundImage = ImageBitmap.imageResource(id = R.drawable.sample_background_game),
                        openGame = true,
                        onClick = {
                            openDialog = true
                            selectedGame = game.android_package_name
                        }
                    )
                }
            }



        }

//        DisturbButton(
//            onClick = { onClickDisturb() },
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 16.dp)
//        )
    }

    AlertDialogShow(
        openDialog = openDialog,
        label = stringResource(id = R.string.profile_open_game_title),
        description = stringResource(id = R.string.profile_open_game_description),
        buttonTextYes = stringResource(id = R.string.yes),
        buttonTextNo = stringResource(id = R.string.cancel),
        onAction = { openGame(selectedGame, context);openDialog = false; selectedGame = "" },
        onClose = { openDialog = false; selectedGame = "" }
    )

}

fun openGame(packageName: String, context: Context){
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    launchIntent?.let { context.startActivity(it) }
}