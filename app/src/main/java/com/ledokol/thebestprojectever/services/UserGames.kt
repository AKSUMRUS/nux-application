package com.ledokol.thebestprojectever.services

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.PowerManager
import android.os.Process
import android.os.UserManager
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.domain.StatusJSON
import com.ledokol.thebestprojectever.ui.components.screens.profile.GameProfile
import com.ledokol.thebestprojectever.ui.components.screens.profile.getIcon
import java.text.SimpleDateFormat
import java.util.*


private const val flags = PackageManager.GET_META_DATA or
        PackageManager.GET_SHARED_LIBRARY_FILES or
        PackageManager.GET_UNINSTALLED_PACKAGES

class GamesStatistic{


    companion object{
        fun checkForPermission(context: Context): Boolean {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
            return mode == AppOpsManager.MODE_ALLOWED
        }

        fun convertApplicationInfoToClassGame(games: List<ApplicationInfo>): List<GameProfile>{
            var newGames: MutableList<GameProfile> = mutableListOf()
            for (game in games){
                newGames.add(GameProfile(game.packageName))
            }

            return newGames
        }


        fun convertApplicationInfoToApps(
            packageManager: PackageManager,
            games: List<ApplicationInfo>
        ): List<StatusJSON>{
            var newGames: MutableList<StatusJSON> = mutableListOf()
            for (game in games){
                newGames.add(StatusJSON(
                    game.packageName,
                    getApplicationLabel(packageManager, game),
                    getApplicationCategory(packageManager, game).toString(),
                ))
            }

            return newGames
        }


        fun getInstalledAppGamesList(packageManager: PackageManager): List<ApplicationInfo> {
            val infos: List<ApplicationInfo> = packageManager.getInstalledApplications(flags)
            val installedApps: MutableList<ApplicationInfo> = ArrayList()
            for (info in infos) {
                if(info.category == ApplicationInfo.CATEGORY_SOCIAL){
                    installedApps.add(info)
                }
            }

            Log.d("INSTALLEDAPPS", installedApps.toString())
            return installedApps
        }


//        fun getIconLargeGameOld(packageName: String, context: Context): Bitmap{
//            val bitmapImage = when(packageName) {
//                "com.nintendo.zara" -> BitmapFactory.decodeResource(context.resources, R.drawable.mario)
//                "com.innersloth.spacemafia" -> BitmapFactory.decodeResource(context.resources, R.drawable.among_us)
////                "com.tencent.ig" -> BitmapFactory.decodeResource(context.resources, R.drawable.pubg)
//                else -> BitmapFactory.decodeResource(context.resources, R.drawable.mario)
//            }
//
//            return bitmapImage
//        }


        fun getIconLargeGame(packageName: String, context: Context): String{
            val bitmapImage = when(packageName) {
                "com.nintendo.zara" -> "https://storage.yandexcloud.net/nux/games_icon_large/mario.jpg"
                "com.innersloth.spacemafia" -> "https://storage.yandexcloud.net/nux/games_icon_large/among_us.jpg"
                "com.tencent.ig" -> "https://storage.yandexcloud.net/nux/games_icon_large/pubg.jpg"
                "com.blizzard.wtcg.hearthstone" -> "https://storage.yandexcloud.net/nux/games_icon_large/hearthstone.png"
                "com.miHoYo.GenshinImpact" -> "https://storage.yandexcloud.net/nux/games_icon_large/genshin.jpg"
                "com.supercell.clashroyale" -> "https://storage.yandexcloud.net/nux/games_icon_large/clash_roayle.jpg"
                "com.axlebolt.standoff2" -> "https://storage.yandexcloud.net/nux/games_icon_large/standoff.jpg"
                "com.mobile.legends" -> "https://storage.yandexcloud.net/nux/games_icon_large/mobile_legends.jpg"
                else -> "https://drive.google.com/drive/u/0/folders/1A6LbsdBDNtLmvBludK7u47wX1vDjmtf0"
            }

            return bitmapImage
        }

        @RequiresApi(VERSION_CODES.O)
        fun convertListApplicationToListStatusJSON(context: Context, packageManager: PackageManager, games: List<ApplicationInfo>): List<StatusJSON> {
            val newGames: MutableList<StatusJSON> = ArrayList()

            for (game in games) {
                val packageName = game.packageName
                val bitmapImagePreview: String = getIconLargeGame(packageName, context)
                val applicationIcon:Drawable = packageManager.getApplicationIcon(packageName)
                var bitmapIcon: Bitmap? = getIcon(
                    context = context,
                    packageManager = context.packageManager,
                    packageName = packageName,
                )

                newGames.add(
                    StatusJSON(
                        packageName,
                        getApplicationLabel(packageManager,game),
                        game.category.toString(),
//                        bitmapImagePreview,
//                        bitmapImagePreview,
//                        bitmapImagePreview,
                    )
                )
            }

            return newGames
        }
    }


    public fun getActiveApp(context: Context, packageManager: PackageManager): String?{
        val userManager = context.getSystemService(Context.USER_SERVICE) as UserManager
        val powerManager = context.getSystemService(POWER_SERVICE) as PowerManager?
        if (userManager.isUserUnlocked && (VERSION.SDK_INT >= VERSION_CODES.KITKAT_WATCH && powerManager!!.isInteractive || VERSION.SDK_INT < VERSION_CODES.KITKAT_WATCH && powerManager!!.isScreenOn)) {
            val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val time = System.currentTimeMillis()
            val appList =
                usm.queryUsageStats(
                    UsageStatsManager.INTERVAL_BEST,
                    time - 10000 * 10000,
                    time
                )
            if (appList != null && appList.size == 0) {
                Log.d("Executed app", "######### NO APP FOUND ##########")
            }
            if (appList != null && appList.size > 0) {
                val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
                for (usageStats in appList) {
//                    Log.d(
//                        "Executed app",
//                        "usage stats executed : " + usageStats.packageName + "\t\t ID: "
//                    )
                    mySortedMap[usageStats.lastTimeUsed] = usageStats
                }
                if (!mySortedMap.isEmpty()) {
                    val currentApp = mySortedMap[mySortedMap.lastKey()]!!.packageName
                    if (!usm.isAppInactive(currentApp)) {
                        return currentApp
                    }
                }
            }
        }

        return null
    }

    fun getStatisticGames(context: Context, packageManager: PackageManager): MutableList<UsageStats> {
        val usageStatsManager = context.getSystemService("usagestats") as UsageStatsManager

        val usageStats = usageStatsManager.queryAndAggregateUsageStats(
            getStartTime(),
            System.currentTimeMillis()
        )

        var games: MutableList<UsageStats> = mutableListOf()

        for ((key,value) in usageStats) {
            try{
                val application: ApplicationInfo = packageManager.getApplicationInfo(key,0)
//                Log.e("APPLICATION_GAME", application.packageName+" "+application.category.toString()+" "+ convertLongToDate(value.lastTimeUsed))
                if(application.category == ApplicationInfo.CATEGORY_SOCIAL){
                    games.add(value)
                }
            }catch (e: PackageManager.NameNotFoundException){
                Log.d("APPLICATION_GAME", key + " Такой пакет не найден")
            }
        }

        return games
    }

}

fun getApplicationIcon(game: Game): ImageBitmap{
//            Log.d("INSTALLEDAPPS", game.icon.toString())
//            try{
    return ImageBitmap(0,0)
//            return null
//            }catch (e: ClassCastException){
//            }

}

fun getApplicationLabel(p: PackageManager, packageInfo: ApplicationInfo): String {
    return p.getApplicationLabel(packageInfo).toString()
}


fun getApplicationCategory(p: PackageManager, packageInfo: ApplicationInfo): Int {
    return p.getApplicationInfo(packageInfo.packageName,0).category
}

@SuppressLint("WrongConstant")
@Composable
fun UserGames() {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val checkPermission = remember{ mutableStateOf(false) }
    val packageManager = context.packageManager
    val installedGames = remember { mutableStateOf(listOf<ApplicationInfo>()) }
    val statisticGames = remember { mutableStateOf(listOf<UsageStats>()) }
//            val stats: MutableList<UsageStats> = ArrayList()
//            stats.addAll(usageStats.values)
//
//            val finalList: List<UsageStatsWrapper> = buildUsageStatsWrapper(installedApps, stats)
//            view.onUsageStatsRetrieved(finalList)
    val gamesStatistic:GamesStatistic = GamesStatistic()

//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_START) {
//                checkPermission.value = GamesStatistic.checkForPermission(context)
//            }else if (event == Lifecycle.Event.ON_RESUME) {
//                checkPermission.value = GamesStatistic.checkForPermission(context)
//
//                if(checkPermission.value){
//                    installedGames.value = GamesStatistic.getInstalledAppGamesList(packageManager)
//                    statisticGames.value = gamesStatistic.getStatisticGames(context, packageManager)
//                }
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

    Log.d("INIT_REQUEST_PERMISSION", "REQUEST_PERMISSION")

    Column(
    ){
        if (!checkPermission.value) {
            Text("Не получен доступ!")
            Button(
                onClick = {
                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                }
            ){
                Text("Получить разрешение")
            }
        }else{
            Text("Доступ получен!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            LazyColumn(
                content = {
                    item {
                        Text(text = "Информация о количестве "+statisticGames.value.size.toString())
                    }
                    items(installedGames.value) { app ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Icon(
                                bitmap = (packageManager.getApplicationIcon(app.packageName) as BitmapDrawable).bitmap.asImageBitmap(),
                                contentDescription = "Аноним",
                                modifier = Modifier
                                    .heightIn(max = 50.dp)
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round))),
                                tint = Color.Unspecified,
                            )

                            Text(
                                text = getApplicationLabel(packageManager, app),
                            )
                            Text(
                                text = app.packageName,
                            )
                        }
                    }
                    items(statisticGames.value) { app ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ){
//                            Icon(
//                                bitmap = (packageManager.getApplicationIcon(app.packageName) as BitmapDrawable).bitmap.asImageBitmap(),
//                                contentDescription = "Аноним",
//                                modifier = Modifier
//                                    .heightIn(max = 50.dp)
//                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.normal_round))),
//                                tint = Color.Unspecified,
//                            )

                            Text(
                                text = app.packageName+" "+convertLongToDate(app.lastTimeUsed)+" "+convertLongToTime(app.totalTimeInForeground),
                            )
                        }
                    }
                },
            )
        }
    }
}

private fun getStartTime(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -1)
    return calendar.timeInMillis
}

fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
    return format.format(date)
}

fun convertLongToTime(time: Long): String {
    val hours = time/3600000
    val minutes = time%3600000/60000
    val seconds = time%60000/1000

    return "$hours:$minutes:$seconds"
}