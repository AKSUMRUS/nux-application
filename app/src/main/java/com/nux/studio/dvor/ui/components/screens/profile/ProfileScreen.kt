package com.nux.studio.dvor.ui.components.screens.profile

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.NonNull
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.iosParameters
import com.google.firebase.ktx.Firebase
import com.nux.studio.dvor.R
import com.nux.studio.dvor.presentation.GamesViewModel
import com.nux.studio.dvor.presentation.ProfileViewModel
import com.nux.studio.dvor.presentation.UserViewModel
import com.nux.studio.dvor.ui.components.atoms.alertdialogs.AlertDialogShow
import com.nux.studio.dvor.ui.components.atoms.texts.Body1
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH4
import com.nux.studio.dvor.ui.components.atoms.texts.HeadlineH5
import com.nux.studio.dvor.ui.components.molecules.games.GameStat
import com.nux.studio.dvor.ui.components.molecules.profile.ProfileTopBlock
import com.nux.studio.dvor.ui.components.molecules.profile.openGame
import java.util.*


fun getIcon(context: Context, packageManager: PackageManager, packageName: String): Bitmap {
    val icon: Drawable = (packageManager.getApplicationIcon(packageName))
    return getBitmapFromDrawable(icon)
}

@NonNull
private fun getBitmapFromDrawable(@NonNull drawable: Drawable): Bitmap {
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
    userViewModel: UserViewModel,
) {

    val context = LocalContext.current
    val games = gamesViewModel.state.games
    var openDialog by remember { mutableStateOf(false) }
    var selectedGame by remember {
        mutableStateOf("")
    }

    val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.WEEK_OF_YEAR, -1)
    val start: Long = calendar.timeInMillis
    val end = System.currentTimeMillis()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            contentPadding = PaddingValues(top = 0.dp, start = 20.dp, end = 20.dp),
            modifier = Modifier,
        ) {
            item {
                Column {
                    ProfileTopBlock(
                        profileViewModel = profileViewModel,
                        navController = navController,
                        userViewModel = userViewModel,
                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .align(CenterHorizontally)
//                    ) {
//
//                        AdditionalBlock(
//                            text = "????????",
//                            openText = "????????????????",
//                            onClick = {
//                                      navController.navigate(ScreenRoutes.CHOOSE_GAMES){
//                                          popUpTo(BottomNavItemMain.Profile.screen_route)
//                                      }
//                            },
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(start = 0.dp, end = 5.dp)
//                        )
//
//                        AdditionalBlock(
//                            text = "????????????",
//                            openText = "????????????????",
//                            onClick = {
//                                navController.navigate(BottomNavItemMain.Friends.screen_route){
//                                    popUpTo(BottomNavItemMain.Profile.screen_route)
//                                }
//                            },
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(start = 20.dp, end = 0.dp)
//                        )
//                    }

                }
            }

            item {
                HeadlineH4(
                    text = stringResource(id = R.string.statistics),
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Body1(
                    "???? ????????????",
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            if (games != null && games.isNotEmpty()) {
                items(games) { game ->
                    GameStat(
                        packageName = game.android_package_name,
                        name = game.name,
                        icon = game.icon_preview!!,
                        openGame = true,
                        onClick = {
                            openDialog = true
                            selectedGame = game.android_package_name
                        },
                        usageTime = game.activity_last_two_weeks ?: 0
                    )
                }
            } else {
                item {
                    Box(
                        Modifier
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(color = MaterialTheme.colors.primary)
                            .fillMaxHeight()
                            .padding(10.dp)
                    ) {
                        HeadlineH5(
                            text = stringResource(id = R.string.not_online_games),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        AlertDialogShow(
            openDialog = openDialog,
            label = stringResource(id = R.string.profile_open_game_title),
            description = stringResource(id = R.string.profile_open_game_description),
            buttonTextYes = stringResource(id = R.string.yes),
            buttonTextNo = stringResource(id = R.string.cancel),
            onActionPrimary = {
                openGame(selectedGame, context);openDialog = false; selectedGame = ""
            },
            onActionSecondary = { openDialog = false; selectedGame = "" }
        )
    }

}

fun getLinkProfile(
    profile_id: String,
): String {
    val dynamicLink = Firebase.dynamicLinks.dynamicLink {
        link = Uri.parse("https://ledokolit.page.link/?profile_id=${profile_id}")
        domainUriPrefix = "https://ledokolit.page.link"
        // Open links with this app on Android
        androidParameters {

        }
        // Open links with com.example.ios on iOS
        iosParameters("com.example.ios") { }
    }

    val dynamicLinkUri = dynamicLink.uri
    return dynamicLinkUri.toString()
}


//ButtonBorder(
//text = "???????????????????? ????????????????",
//padding = 2.dp,
//modifier = Modifier.padding(start = 10.dp),
//onClick = {
//    val dynamicLinkUri = getLinkProfile(profile!!.id)
//    Log.e("dynamicLinkUri", dynamicLinkUri.toString())
//
//    val intent= Intent()
//    intent.action = Intent.ACTION_SEND
//    intent.putExtra(Intent.EXTRA_TEXT, "???????????????? ???????? ?? ???????????? ?? Dvor ${dynamicLinkUri.toString()}")
//    intent.type="text/plain"
//
//    context.startActivity(Intent.createChooser(intent,"????????????????????"))
//}
//)