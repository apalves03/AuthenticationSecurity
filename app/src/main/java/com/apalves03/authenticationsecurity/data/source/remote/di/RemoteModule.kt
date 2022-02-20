package com.apalves03.authenticationsecurity.data.source.remote.di

import com.apalves03.authenticationsecurity.data.source.PushNotificationDataSource
import com.apalves03.authenticationsecurity.data.source.remote.PushNotificationRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
open class RemoteModule {

    @Singleton
    @Provides
    fun providePushNotificationRemoteDataSource(): PushNotificationDataSource {
        return PushNotificationRemoteDataSource.create()
    }

}