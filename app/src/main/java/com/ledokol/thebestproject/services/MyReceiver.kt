package com.ledokol.thebestproject.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    //    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent!!.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast.makeText(context.applicationContext, "Hello World!", Toast.LENGTH_LONG).show()
//            Toast toast = Toast.makeText(context.getApplicationContext(),
//            context.getResources().getString(R.string.your_message), Toast.LENGTH_LONG);
//            toast.show();
            Log.d("myapp", "Hello World!")
            // ваш код здесь
        }
//        Log.e("onReceiveWork","Start")
        val intentService = Intent(context, MyService::class.java)
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startForegroundService(intentService)
    }
}