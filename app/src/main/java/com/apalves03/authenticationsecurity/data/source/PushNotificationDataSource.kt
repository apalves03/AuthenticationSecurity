package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.data.SendNotification

/**
 * Main entry point to request push notification.
 */
interface PushNotificationDataSource {

    suspend fun sendNotification(sendNotification: SendNotification)
}