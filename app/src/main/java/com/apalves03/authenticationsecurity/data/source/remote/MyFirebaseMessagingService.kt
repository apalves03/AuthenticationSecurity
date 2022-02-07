package com.apalves03.authenticationsecurity.data.source.remote

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.apalves03.authenticationsecurity.MyApplication
import com.apalves03.authenticationsecurity.util.sendNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check messages for notification and call sendNotification
        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body as String, remoteMessage?.data)
        }

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * InstanceID token is initially generated so this is where you would retrieve
     * the token.
     */
    override fun onNewToken(token: String?) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    /**
     * Persist token to third-party (your app) servers.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        token?.let { registerDeviceTokenFcm(it) }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     * @param data message payload data received.
     */
    private fun sendNotification(messageBody: String, data: Map<String, String>?) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java) as NotificationManager
        notificationManager.sendNotification(messageBody, data, applicationContext)
    }

    fun registerDeviceTokenFcm(deviceTokenFcm: String) {
        assert(deviceTokenFcm != null)

        val userManager = (application as MyApplication).appComponent.userManager()

        userManager.registerDeviceTokenFcm(deviceTokenFcm)
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}