package com.ledokol.thebestproject.ui.components.screens.profile

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.annotation.NonNull
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.BottomNavigationItem
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
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
import com.ledokol.thebestproject.R
import com.ledokol.thebestproject.data.local.profile.ProfileEvent
import com.ledokol.thebestproject.presentation.GamesViewModel
import com.ledokol.thebestproject.presentation.ProfileViewModel
import com.ledokol.thebestproject.presentation.UserViewModel
import com.ledokol.thebestproject.ui.components.atoms.alertdialogs.AlertDialogShow
import com.ledokol.thebestproject.ui.components.atoms.buttons.ButtonBorder
import com.ledokol.thebestproject.ui.components.atoms.texts.Body1
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH4
import com.ledokol.thebestproject.ui.components.atoms.texts.HeadlineH5
import com.ledokol.thebestproject.ui.components.molecules.GameInList
import com.ledokol.thebestproject.ui.components.molecules.GameStat
import com.ledokol.thebestproject.ui.components.molecules.profile.AdditionalBlock
import com.ledokol.thebestproject.ui.components.molecules.profile.ProfileTopBlock
import com.ledokol.thebestproject.ui.components.molecules.profile.StatisticsBlock
import com.ledokol.thebestproject.ui.navigation.BottomNavItemMain
import java.util.*


fun getIcon(context: Context, packageManager: PackageManager, packageName: String): Bitmap? {
    val icon: Drawable = (packageManager.getApplicationIcon(packageName))
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
    userViewModel: UserViewModel,
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

    val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.WEEK_OF_YEAR, -1)
    val start: Long = calendar.getTimeInMillis()
    val end = System.currentTimeMillis()
    val stats: Map<String, UsageStats> = usageStatsManager.queryAndAggregateUsageStats(start, end)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LazyColumn(
            contentPadding = PaddingValues(top = 0.dp, start = 20.dp, end = 20.dp),
            modifier = Modifier,
        ) {
            item(
            ) {
                Column(
                ) {
                    ProfileTopBlock(
                        profileViewModel = profileViewModel,
                        navController = navController,
                        userViewModel = userViewModel,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(CenterHorizontally)
                    ) {

                        AdditionalBlock(
                            text = "Игры",
                            openText = "Смотреть",
                            onClick = {
                                      navController.navigate(BottomNavItemMain.QuickGame.screen_route){
                                          popUpTo(BottomNavItemMain.Profile.screen_route)
                                      }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 0.dp, end = 5.dp)
                        )

                        AdditionalBlock(
                            text = "Друзья",
                            openText = "Добавить",
                            onClick = {
                                navController.navigate(BottomNavItemMain.Friends.screen_route){
                                    popUpTo(BottomNavItemMain.Profile.screen_route)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 20.dp, end = 0.dp)
                        )

                    }

                }
            }

            item(){
                HeadlineH4(
                    text = stringResource(id = R.string.statistics),
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp)
                )

                Body1(
                    "за неделю",
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            if (games != null) {
                items(games.sortedBy {
                    -(stats.get(it.android_package_name)!!.totalTimeInForeground.toInt()/60000)
                }) { game ->
                    GameStat(
                        packageName = game.android_package_name,
                        name = game.name,
                        icon = game.icon_preview!!,
                        iconLarge = game.icon_large!!,
                        backgroundImage = ImageBitmap.imageResource(id = R.drawable.sample_background_game),
                        openGame = true,
                        onClick = {
                            openDialog = true
                            selectedGame = game.android_package_name
                        },
                        usageTime = if(game.android_package_name in stats.keys)
//                            (stats.get(game.android_package_name)!!.totalTimeInForeground.milliseconds).toString()
                            (stats.get(game.android_package_name)!!.totalTimeInForeground.toInt()/60000).toString()
//                            null
                        else null
                    )
                }
            }else{
                item(){
                    Box(
                        Modifier
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(color = MaterialTheme.colors.primary)
                            .fillMaxHeight()
                            .padding(10.dp)
                    ) {
                        HeadlineH5(
                            text = "У вас нету ни одной игры",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }


        }
    }

}

fun getLinkProfile(
    profile_id: String,
) : String {
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
//text = "Поделиться профилем",
//padding = 2.dp,
//modifier = Modifier.padding(start = 10.dp),
//onClick = {
//    val dynamicLinkUri = getLinkProfile(profile!!.id)
//    Log.e("dynamicLinkUri", dynamicLinkUri.toString())
//
//    val intent= Intent()
//    intent.action = Intent.ACTION_SEND
//    intent.putExtra(Intent.EXTRA_TEXT, "Добавляй меня в друзья в Dvor ${dynamicLinkUri.toString()}")
//    intent.type="text/plain"
//
//    context.startActivity(Intent.createChooser(intent,"Поделиться"))
//}
//)