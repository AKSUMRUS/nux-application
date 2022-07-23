package com.ledokol.thebestprojectever.ui.components.screens.profile

import com.ledokol.thebestprojectever.ui.components.molecules.profile.ProfileTopBlock
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.NonNull
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.presentation.StatusViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.alertdialogs.AlertDialogShow
import com.ledokol.thebestprojectever.ui.components.atoms.buttons.ButtonPrimary
import com.ledokol.thebestprojectever.ui.components.molecules.GameInList


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
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
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
    val packageManager = context.packageManager
    var openDialog by remember{ mutableStateOf(false) }
    var selectedGame by remember {
        mutableStateOf("")
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 0.dp, start = 0.dp, end = 0.dp),
        modifier = Modifier
        ,
    ) {
        item (
            span = {GridItemSpan(2)}
        ){
            Column(

            ){
                ProfileTopBlock(
                    profileViewModel = profileViewModel,
                    statusViewModel = statusViewModel,
                    navController = navController
                )
            }
        }

        if(games!=null){
            items(games){ game ->
                GameInList(
                    packageName = game.android_package_name,
                    name = game.name,
//                    Временно!
                    icon = "https://storage.yandexcloud.net/nux/icons/icon_preview/"+game.android_package_name+".png",
                    iconLarge = "https://storage.yandexcloud.net/nux/icons/icon_large/"+game.android_package_name+".png",
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

    AlertDialogShow(
        openDialog = openDialog,
        label = "Открыть игру?",
        description = "Нажми да, если хочешь запустить игру прямо сейчас",
        buttonTextYes = "Да",
        buttonTextNo = "Отмена",
        onAction = { openGame(selectedGame,context);openDialog = false; selectedGame = "" },
        onClose = {openDialog = false; selectedGame = ""}
    ) 

}

fun openGame(packageName: String, context: Context){
    val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
    launchIntent?.let { context.startActivity(it) }
}