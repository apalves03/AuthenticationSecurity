package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.data.source.remote.FirebaseApiService
import com.apalves03.authenticationsecurity.data.SendNotification
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val service: FirebaseApiService) {

    suspend fun sendNotification(sendNotification: SendNotification) {
        service.sendNotification(sendNotification)
    }

}