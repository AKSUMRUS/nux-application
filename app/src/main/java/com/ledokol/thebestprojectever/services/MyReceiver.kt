package com.ledokol.thebestprojectever.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.e("onReceiveWork","Start")
        val intentService = Intent(context, MyService::class.java)
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startForegroundService(intentService)
    }
}