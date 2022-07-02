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
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ledokol.thebestprojectever.MainActivity
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.ui.components.molecules.GamesStatistic
import com.ledokol.thebestprojectever.ui.components.screens.toast


class MyService : Service() {

    private var notificationManager: NotificationManager? = null
    val NOTIFICATION_ID = 4389138
    val CHANNEL_ID = "LEDOKOL"
    val context: Context = this

    @Nullable
    override fun onBind (intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val packageManager: PackageManager = context.packageManager
        createNotification()

        doTask(packageManager)
        return START_STICKY;
    }

    fun logApps(text: String){
        Log.d("APP_ACTIVE", text)
    }

    fun doTask(packageManager: PackageManager){
        val gamesStatistic = GamesStatistic()

        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            val activeApp = gamesStatistic.getActiveApp(context, packageManager)

            if(activeApp==null){
                logApps("Сейчас нету запущенных приложений")
//                toast("Сейчас нету запущенных приложений")
            }else{
                logApps("Сейчас запущено приложение $activeApp")
//                toast("Сейчас запущено приложение $activeApp")
            }
            runnable?.let { handler.postDelayed(it, 3000) }
        }

        handler.postDelayed(runnable, 3000)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        //create a intent that you want to start again..
        val intent = Intent(applicationContext, MyService::class.java)
        val pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000] =
            pendingIntent
        super.onTaskRemoved(rootIntent)
    }

    private fun createNotification() {
//        toast("Start notification")
        val context: Context = this

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setOngoing(true)
            .setSmallIcon(R.drawable.anonymous)
            .setContentTitle("TheBestProjectEver работает...")
            .setContentText("Ваши данные в безопасности")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis());


        with(NotificationManagerCompat.from(context)) {
            if (getNotificationChannel(CHANNEL_ID) == null) {
                createNotificationChannel(context)
            }
            startForeground(NOTIFICATION_ID, builder.build())
//            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val descriptionText = "Уведомление"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //Removing any notifications
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)

        //Disabling service
        stopSelf()
    }
}
