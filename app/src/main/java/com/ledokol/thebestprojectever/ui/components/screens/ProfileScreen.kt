package com.ledokol.thebestprojectever.ui.components.screens

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.ProfileViewModel
import com.ledokol.thebestprojectever.services.GamesStatistic
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.molecules.*
import java.lang.Exception
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sqrt


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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    gamesViewModel: GamesViewModel,
){
    val context = LocalContext.current
    val games = gamesViewModel.state.games
    val packageManager = context.packageManager

//    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
//    val checkPermission = remember{ mutableStateOf(false) }

//    DisposableEffect(lifecycleOwner, checkPermission) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_START) {
//                checkPermission.value = GamesStatistic.checkForPermission(context)
//
//                if(checkPermission.value && (gamesViewModel!=null && gamesViewModel.state.games!!.isEmpty())){
//                    Log.d("INSTALLEDAPPS", "UPDATE CHECK")
//                    gamesViewModel.clearGames()
//                    gamesViewModel.insertGames(
//                        GamesStatistic.convertListApplicationToListGame(
//                            context,
//                            context.packageManager,
//                            GamesStatistic.getInstalledAppGamesList(context.packageManager)
//                        )
//                    )
//                    gamesViewModel.getGames()
//                    gamesViewModel.shareGames()
//                }
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

    LazyColumn(content = {
        item {
            ProfileTopBlock(
                profileViewModel = profileViewModel,
            )
        }

        if(games!=null){
            items(games){ game ->
                GameInList(
                    name = game.name,
                    icon = getIcon(context, packageManager, game.android_package_name)!!.asImageBitmap(),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.sample_background_game),
                )
            }
        }
    })

}