package com.apalves03.authenticationsecurity.data.source.remote.di

import com.apalves03.authenticationsecurity.data.source.remote.FirebaseApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Tells Dagger this is a Dagger module
@Module
open class NetworkModule {

    @Singleton
    @Provides
    fun provideFirebaseApiService(): FirebaseApiService {
        return FirebaseApiService.create()
    }

}