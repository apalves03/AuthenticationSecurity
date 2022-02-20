package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.data.SendNotification

/**
 * Interface to the data layer.
 */
interface PushNotificationRepository {

    suspend fun sendNotification(sendNotification: SendNotification)
}