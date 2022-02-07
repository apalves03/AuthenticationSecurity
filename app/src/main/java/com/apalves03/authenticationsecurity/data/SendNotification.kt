package com.apalves03.authenticationsecurity.data

import com.squareup.moshi.Json

data class SendNotification (
    // used to map to from the JSON to token in our class
    @Json(name = "to") val token: String,
    val notification: Notification,
    val data: Data
)