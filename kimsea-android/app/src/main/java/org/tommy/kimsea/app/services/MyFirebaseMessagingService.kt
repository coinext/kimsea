package org.tommy.kimsea.app.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.tommy.kimsea.app.R
import org.tommy.kimsea.app.SplashActivity
import org.tommy.kimsea.app.configs.Constants
import java.io.BufferedInputStream
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage != null && remoteMessage.data.size > 0) {
            sendNotification(remoteMessage)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]
        val imgUrl = remoteMessage.data["img"]

        val CHANNEL_ID = "ChannerID"
        val mManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val CHANNEL_NAME = "ChannerName"
            val CHANNEL_DESCRIPTION = "ChannerDescription"
            val importance = NotificationManager.IMPORTANCE_HIGH

            // add in API level 26
            val mChannel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            mChannel.description = CHANNEL_DESCRIPTION
            mChannel.enableLights(true)
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
            mChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            mManager.createNotificationChannel(mChannel)
        }

        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var style:NotificationCompat.BigPictureStyle? = null
        if (imgUrl != null && imgUrl.isNotBlank()) {
            val url = URL(imgUrl)
            val conn = url.openConnection()
            conn.connect()
            val bis = BufferedInputStream(conn.getInputStream())
            val imgBitmap = BitmapFactory.decodeStream(bis)
            bis.close()

            style = NotificationCompat.BigPictureStyle()
            style.setBigContentTitle(title)
            style.setSummaryText(message)
            style.bigPicture(imgBitmap)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.noti_icon)
        builder.setAutoCancel(true)
        builder.setPriority(Notification.PRIORITY_MAX)
        builder.setDefaults(Notification.DEFAULT_ALL)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentTitle(title)
        builder.setContentText(message)
        builder.setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setContentTitle(title)
            builder.setVibrate(longArrayOf(500, 500))
        }
        if (imgUrl != null && imgUrl.isNotBlank()) {
            builder.setStyle(style)
        }
        mManager.notify(0, builder.build())
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {

        Constants.PUTH_TOKEN = token
        //Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token)
    }
}