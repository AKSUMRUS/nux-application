package com.ledokol.thebestprojectever.presentation

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.PowerManager
import android.os.UserManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ledokol.thebestprojectever.data.local.game.Game
import com.ledokol.thebestprojectever.data.local.game.GameState
import com.ledokol.thebestprojectever.data.repository.GamesRepository
import com.ledokol.thebestprojectever.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val flags = PackageManager.GET_META_DATA or
        PackageManager.GET_SHARED_LIBRARY_FILES or
        PackageManager.GET_UNINSTALLED_PACKAGES



@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepository
): ViewModel() {

    var state by mutableStateOf(GameState())

    init {
//        getGames()
    }

    fun clearGames(){
        viewModelScope.launch {
            repository.clearGames()
        }
    }

    fun insertGame(game : Game){
        viewModelScope.launch {
            repository.insertGame(game)
        }
    }

    fun insertGames(games: List<Game>){
        for (game in games){
            insertGame(game)
        }
    }


    fun getGames(
        query: String = state.searchQuery.lowercase()
    ){
        viewModelScope.launch {
            repository.getGames(false,query)
                .collect{ result ->
                        when(result){
                            is Resource.Success -> {
                                result.data.let { games ->
                                    state = state.copy(
                                        games = games
                                    )
                                }
                                Log.e("GAMES",state.toString())
                            }
                            is Resource.Error -> Unit
                            is Resource.Loading -> {
                                state = state.copy(
                                    isLoading = result.isLoading
                                )
                            }
                        }

                }
        }
    }

    fun getGame(
        query: String = state.searchQuery.lowercase()
    ){
        viewModelScope.launch {
            repository.getGame(query)
                .collect{ result ->
                    when(result){
                        is Resource.Success -> {
                            result.data.let { game ->
                                state = state.copy(
                                    game = game
                                )
                            }
                            Log.e("GAME",state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }

                }
        }
    }



    //New Function


    @RequiresApi(Build.VERSION_CODES.O)
    fun getInstalledAppGamesList(packageManager: PackageManager): List<ApplicationInfo> {
        val infos: List<ApplicationInfo> = packageManager.getInstalledApplications(flags)
        val installedApps: MutableList<ApplicationInfo> = ArrayList()
        for (info in infos) {
            if(info.category == ApplicationInfo.CATEGORY_GAME){
                installedApps.add(info)
            }
        }

        Log.d("INSTALLEDAPPS", installedApps.toString())
        return installedApps
    }


    @RequiresApi(Build.VERSION_CODES.N)
    public fun getActiveApp(context: Context, packageManager: PackageManager): String?{
        val userManager = context.getSystemService(Context.USER_SERVICE) as UserManager
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager?
        if (userManager.isUserUnlocked && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH && powerManager!!.isInteractive || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH && powerManager!!.isScreenOn)) {
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
                    mySortedMap!![usageStats.lastTimeUsed] = usageStats
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    val currentApp = mySortedMap[mySortedMap.lastKey()]!!.packageName
                    if (!usm.isAppInactive(currentApp)) {
                        return currentApp
                    }
                }
            }
        }

        return null
    }

    fun shareGames(){
        viewModelScope.launch {
            repository.shareGames()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
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
                if(application.category == ApplicationInfo.CATEGORY_GAME){
                    games.add(value)
                }
            }catch (e: PackageManager.NameNotFoundException){
                Log.d("APPLICATION_GAME", key + " Такой пакет не найден")
            }
        }

        return games
    }
}

private fun getStartTime(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -1)
    return calendar.getTimeInMillis()
}
