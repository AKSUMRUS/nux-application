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
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.presentation.GamesViewModel
import com.ledokol.thebestprojectever.presentation.MainViewModel
import com.ledokol.thebestprojectever.ui.components.atoms.*
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic
import com.ledokol.thebestprojectever.ui.components.molecules.UserInformationProfile
import com.ledokol.thebestprojectever.ui.components.molecules.UserOverviewProfile
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
//    try{
//
//    }catch (e: Exception){
//        return Bitmap.createBitmap(0,0,)
//    }
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
    mainViewModel: MainViewModel,
    gamesViewModel: GamesViewModel,
){
    val context = LocalContext.current
    val games = gamesViewModel.state.games

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val checkPermission = remember{ mutableStateOf(false) }

    DisposableEffect(lifecycleOwner, checkPermission) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                checkPermission.value = GamesStatistic.checkForPermission(context)

                if(checkPermission.value){
                    gamesViewModel.clearGames()
                    gamesViewModel.insertGames(
                        GamesStatistic.convertListApplicationToListGame(
                            context.packageManager,
                            GamesStatistic.getInstalledAppGamesList(context.packageManager)
                        )
                    )
                }
            }else if (event == Lifecycle.Event.ON_RESUME) {
                checkPermission.value = GamesStatistic.checkForPermission(context)

                if(checkPermission.value){
                    gamesViewModel.clearGames()
                    gamesViewModel.insertGames(
                        GamesStatistic.convertListApplicationToListGame(
                            context.packageManager,
                            GamesStatistic.getInstalledAppGamesList(context.packageManager)
                        )
                    )
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.background
            )
//            .verticalScroll(rememberScrollState())
    ) {
        UserInformationProfile(
            name = "Гордей",
            cntGames = 5,
            cntFriends = 17,
        )

        Button(
            text = stringResource(id = R.string.logout),
            onClick = {
                mainViewModel.clearProfile()
            }
        )

        if(!checkPermission.value){
            Text("Предоставьте, пожалуйста, доступ!")
            androidx.compose.material.Button(
                onClick = {
                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }
            ) {
                Text("Получить разрешение")
            }
        }else{
            UserOverviewProfile(games = games)
//            UserGames()
        }
    }
}

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)

