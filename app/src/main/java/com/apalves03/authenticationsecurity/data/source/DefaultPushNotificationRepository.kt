package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.data.SendNotification
import javax.inject.Inject

/**
 * Data layer implementation to request push notification.
 */
class DefaultPushNotificationRepository @Inject constructor(
    private val pushNotificationRemoteDataSource: PushNotificationDataSource
) : PushNotificationRepository {

    override suspend fun sendNotification(sendNotification: SendNotification) {
        pushNotificationRemoteDataSource.sendNotification(sendNotification)
    }

}