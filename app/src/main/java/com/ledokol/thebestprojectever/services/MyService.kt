package com.ledokol.thebestprojectever.services

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
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ledokol.thebestprojectever.MainActivity
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.repository.ProfileRepository
import com.ledokol.thebestprojectever.data.repository.StatusRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val NOTIFICATION_ID = 101
private const val CHANNEL_ID = "LEDOKOL"

@AndroidEntryPoint
class MyService: Service() {

    @Inject
    lateinit var statusRepository: StatusRepository
    @Inject
    lateinit var profileRepository: ProfileRepository

    private var notificationManager: NotificationManager? = null
    val context: Context = this

    @Nullable
    override fun onBind (intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("Service onCreate",statusRepository.toString())
//        statusRepository.setStatus("","","")

        createNotification()
        doTask(packageManager)

        notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(@Nullable intent: Intent?, flags: Int, startId: Int): Int {

//        val packageManager: PackageManager = context.packageManager
        createNotification()

        return START_STICKY;
    }

    private fun logApps(text: String){
        Log.d("APP_ACTIVE", text)
    }

    private fun doTask(packageManager: PackageManager){
        val gamesStatistic = GamesStatistic()
        var checkLeave: Boolean = false

        profileRepository.getProfile()

        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            val activeAppPackage = gamesStatistic.getActiveApp(context, packageManager)

            if(activeAppPackage==null){
                logApps("Сейчас нету запущенных приложений")
                if(!checkLeave){
                    checkLeave = true
                    statusRepository.leaveStatus(accessToken = profileRepository.data.access_token)
                }
            }else{
                val activeAppInfo = packageManager.getApplicationInfo(activeAppPackage,0)
                val packageApp = activeAppInfo.packageName
                val labelApp = getApplicationLabel(packageManager, activeAppInfo)
                val categoryApp = getApplicationCategory(packageManager, activeAppInfo)

                Log.i("DataActiveApp", "$packageApp $labelApp $categoryApp")

                profileRepository.data.let{
                    Log.e("STATUS!!!!",profileRepository.data.toString())
                    statusRepository.setStatus(
                        packageApp,
                        labelApp,
                        categoryApp,
                        accessToken = profileRepository.data.access_token
                    )
                }

                checkLeave = false
                    logApps("Сейчас запущено приложение $activeAppPackage")
            }
            runnable?.let { handler.postDelayed(it, 5000) }
        }

        handler.postDelayed(runnable, 3000)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        //create a intent that you want to start again..
        super.onTaskRemoved(rootIntent)

        val intent = Intent(applicationContext, MyService::class.java)
        intent.action = Intent.ACTION_MAIN;

        val pendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000] =
            pendingIntent
    }

    private fun createNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.action = Intent.ACTION_MAIN;
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.star)
            .setContentTitle("Следим за активностью друзей...")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setWhen(System.currentTimeMillis());


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
            val descriptionText = "Уведомление"
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