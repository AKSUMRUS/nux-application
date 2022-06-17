package com.ledokol.thebestprojectever.services

import android.R
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.ledokol.thebestprojectever.MainActivity
import com.ledokol.thebestprojectever.ui.components.screens.toast
import java.util.*


class MyService : Service() {

    private val NOTIF_ID = 2
    private val NOTIF_CHANNEL_ID = "com.ledokol.thebestprojectever"

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // do your jobs here
        startForeground()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )
        startForeground(
            NOTIF_ID, NotificationCompat.Builder(
                this,
                NOTIF_CHANNEL_ID
            ) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(R.drawable.edit_text)
                .setContentTitle("Hello World!")
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build()
        )
    }
//    private lateinit var mHandler: Handler
//    private lateinit var mRunnable: Runnable
//
//    override fun onBind(intent: Intent): IBinder? {
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//
//    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//        // Send a notification that service is started
//        Log.d("START_SERVICE","START_SERVICE")
//        toast("Service started.")
//
//        // Do a periodic task
//        mHandler = Handler()
//        mRunnable = Runnable { showRandomNumber() }
//        mHandler.postDelayed(mRunnable, 5000)
//
//        return START_STICKY
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        toast("Service destroyed.")
////        mHandler.removeCallbacks(mRunnable)
//    }
//
//    // Custom method to do a task
//    private fun showRandomNumber() {
//        val rand = Random()
//        val number = rand.nextInt(100)
//        toast("Random Number : $number")
//        mHandler.postDelayed(mRunnable, 5000)
//    }
}