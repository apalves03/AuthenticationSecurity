package com.apalves03.authenticationsecurity.data.source.remote

import com.apalves03.authenticationsecurity.data.SendNotification
import com.apalves03.authenticationsecurity.data.source.PushNotificationDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Used to connect to the FirebaseAPI to send notifications.
 *
 */
interface PushNotificationRemoteDataSource : PushNotificationDataSource {

    @Headers(
        "Authorization: key=$SERVER_KEY",
        "Content-Type:application/json")
    @POST("fcm/send")
    override suspend fun sendNotification(@Body sendNotification: SendNotification)

    /**
     *  A companion object is similar to other objects, such as instances of a class.
     *
     *  A single instance of a companion object will exist throughout your program,
     *  so called a singleton pattern.
     */
    companion object {

        // Create a property for the base URL
        const val BASE_URL = "https://fcm.googleapis.com/"

        const val SERVER_KEY = "AAAAVBPGDG0:APA91bFEeRd13WtJ3OXvLDvwPLU4CrLRZBpAxWioQklE" +
                "pKroqK2N7VIRjE0n20IaNSwictwyfVEZMIl4YC5gD0p_KofSfxXcrHpEvKxCKhl-nn9zeQX" +
                "G0eAaCY1O_h8bkW0N36qRJ4BS"

        /**
         * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
         * full Kotlin compatibility.
         */
        private val mochi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        /**
         * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
         * object.
         */
        fun create(): PushNotificationRemoteDataSource {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(mochi))
                .build()
                .create(PushNotificationRemoteDataSource::class.java)
        }

    }

}