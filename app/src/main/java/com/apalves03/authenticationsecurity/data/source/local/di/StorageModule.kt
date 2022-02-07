package com.apalves03.authenticationsecurity.data.source.local.di

import com.apalves03.authenticationsecurity.data.source.local.SharedPreferencesStorage
import com.apalves03.authenticationsecurity.data.source.local.Storage
import dagger.Binds
import dagger.Module

// Tells Dagger this is a Dagger module
@Module
abstract class StorageModule {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage
}
