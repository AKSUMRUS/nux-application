package com.ledokol.thebestprojectever.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val intentService = Intent(context, MyService::class.java)
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intentService)
    }
}