package com.ledokol.thebestprojectever.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ledokol.thebestprojectever.MainActivity
import com.ledokol.thebestprojectever.R
import com.ledokol.thebestprojectever.data.repository.ProfileRepository
import com.ledokol.thebestprojectever.data.repository.UsersRepository
import javax.inject.Inject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: UsersRepository

    var counterFriendEnteredApp = 1;
    var counterFriendInviteToApp = 1;

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

//        sendNotification("User", "Приглашение в игру", "yio.tro.onliyoy")
        // Check if message contains a data payload.
//        sendNotificationFriendEnteredApp("ffdfd", "Друг зашел в игру","ffdklfdjkl")
        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data
//            sendNotification(data["user_nickname"].toString(), "Приглашение в игру")
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if(data["type"] == "friend_entered_app"){
                Glide.with(this)
                    .asBitmap()
                    .load(data["app_icon_preview"].toString())
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            sendNotificationFriendEnteredApp(
                                data["user_nickname"].toString(),
                                "Начал играть в ${data["app_name"]}",
                                data["app_android_package_name"].toString(),
                                resource,
                                data["user_id"].toString()
                            )
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {}

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })

            }else{
                Glide.with(this)
                    .asBitmap()
                    .load(data["app_icon_preview"].toString())
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            sendNotificationInviteToApp(
                                data["user_nickname"].toString(),
                                "Приглашает в игру ${data["app_name"]}",
                                data["app_android_package_name"].toString(),
                                resource,
                                data["user_id"].toString()
                            )
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {}

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })


            }

            if (/* Check if data needs to be processed by long running job */ false) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
//        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotificationFriendEnteredApp(
        messageTitle: String,
        messageBody: String,
        packageGame: String,
        iconGame: Bitmap,
        notificationId: String,
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("notification_id", notificationId)
        intent.putExtra("gamePackageName_$notificationId", packageGame)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val join = startActivity(getPackageManager().getLaunchIntentForPackage(packageGame))

        val channelId = "ChannelId1"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.star)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setLargeIcon(
                iconGame
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(counterFriendEnteredApp++ /* ID of notification */, notificationBuilder.build())
    }


    private fun sendNotificationInviteToApp(
        messageTitle: String,
        messageBody: String,
        packageGame: String,
        iconGame: Bitmap,
        notificationId: String,
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("notification_id", notificationId)
        intent.putExtra("gamePackageName_$notificationId", packageGame)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

//        val image = BitmapFactory.decodeStream(iconGame.openStream())
        val context: Context = this

        val channelId = "ChannelId2"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.star)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setLargeIcon(
                iconGame
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)


        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        val channel = NotificationChannel(channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(counterFriendInviteToApp++ /* ID of notification */, notificationBuilder.build())
    }

    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }
}