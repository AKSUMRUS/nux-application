package com.nux.studio.dvor.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nux.studio.dvor.MainActivity
import com.nux.studio.dvor.R
import com.nux.studio.dvor.data.repository.ProfileRepository
import com.nux.studio.dvor.data.repository.StatusRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val NOTIFICATION_ID = 101
private const val CHANNEL_ID = "LEDOKOL"

@AndroidEntryPoint
class MyService : Service() {

    @Inject
    lateinit var statusRepository: StatusRepository

    @Inject
    lateinit var profileRepository: ProfileRepository

    private var notificationManager: NotificationManager? = null
    val context: Context = this

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("Service onCreate", statusRepository.toString())
        profileRepository.getProfile()

        createNotification()
        doTask(packageManager)

        notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(@Nullable intent: Intent?, flags: Int, startId: Int): Int {

//        val packageManager: PackageManager = context.packageManager
        createNotification()

        return START_STICKY
    }

    private fun logApps(text: String) {
        Log.d("APP_ACTIVE", text)
    }

    private fun doTask(packageManager: PackageManager) {
        val gamesStatistic = GamesStatistic()
        var checkLeave: Boolean = false
        var lastApp: String? = null

        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            try {
                val activeAppPackage = gamesStatistic.getActiveApp(context, packageManager)

                if (activeAppPackage == null) {
                    logApps("???????????? ???????? ???????????????????? ????????????????????")
                    if (lastApp != null) {
                        if (!checkLeave) {
                            lastApp = null
                            checkLeave = true
                            statusRepository.leaveStatus()
                        }
                    }
                } else {
                    val activeAppInfo = packageManager.getApplicationInfo(activeAppPackage, 0)
                    val packageApp = activeAppInfo.packageName
                    val labelApp = getApplicationLabel(packageManager, activeAppInfo)
                    val categoryApp = getApplicationCategory(packageManager, activeAppInfo)

                    Log.i("DataActiveApp", "$packageApp $labelApp $categoryApp")

                    if (lastApp != packageApp) {
                        profileRepository.data.let {

                            Log.i(
                                "STATUS!!!!",
                                "$packageApp $labelApp $categoryApp"
                            )

                            statusRepository.putStatistics()

                            statusRepository.setStatus(
                                packageApp,
                                labelApp,
                                categoryApp,
                            )
                        }
                        lastApp = packageApp
                    }

                    checkLeave = false
                    logApps("???????????? ???????????????? ???????????????????? $activeAppPackage")
                }
            } catch (e: Exception) {
                lastApp = null
                checkLeave = true
                statusRepository.leaveStatus()
                Log.e("Service", e.toString())
            }
            runnable?.let { handler.postDelayed(it, 20000) }
        }

        handler.postDelayed(runnable, 1000)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        //create a intent that you want to start again..
        super.onTaskRemoved(rootIntent)

        val intent = Intent(applicationContext, MyService::class.java)
        intent.action = Intent.ACTION_MAIN

        val pendingIntent = PendingIntent.getService(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000] =
            pendingIntent
    }

    private fun createNotification() {
        val intent = Intent(this, com.nux.studio.dvor.MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.star)
            .setContentTitle("?????????????? ?????? ??????????????")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setWhen(System.currentTimeMillis())


        with(NotificationManagerCompat.from(context)) {
            if (getNotificationChannel(CHANNEL_ID) == null) {
                createNotificationChannel(context)
            }
            startForeground(5775, builder.build())
//            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Ledokol"
            val descriptionText = "??????????????????????"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //Removing any notifications
        notificationManager!!.cancel(NOTIFICATION_ID)

        //Disabling service
        stopSelf()

    }
}