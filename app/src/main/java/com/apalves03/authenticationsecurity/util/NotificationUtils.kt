package com.apalves03.authenticationsecurity.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.apalves03.authenticationsecurity.AuthenticationSecurityActivity
import com.apalves03.authenticationsecurity.R


private val NOTIFICATION_ID = 0
const val CODE_EXTRA = "code"

/**
 * Extension function to send messages
 *
 * Builds and delivers the notification.
 *
 * @param messageBody, notification text.
 * @param applicationContext, activity context.
 */
fun NotificationManager.sendNotification(messageBody: String, data: Map<String, String>?, applicationContext: Context) {


    // Create intent
    val contentIntent = Intent(applicationContext, AuthenticationSecurityActivity::class.java)

    data?.let {
        if (data.containsKey(CODE_EXTRA)) {
            contentIntent.putExtra(CODE_EXTRA, data.get(CODE_EXTRA))
        }
    }

    // Create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        // Use the new notification channel
        applicationContext.getString(R.string.app_notification_channel_id)
    )
        // Set title, text and icon to builder
        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        // Set content intent
        .setContentIntent(contentPendingIntent)
        // Set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    // Call notify
    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
