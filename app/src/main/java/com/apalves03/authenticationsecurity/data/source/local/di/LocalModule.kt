package com.apalves03.authenticationsecurity.data.source.local.di

import com.apalves03.authenticationsecurity.data.source.AuthenticationSecurityDataSource
import com.apalves03.authenticationsecurity.data.source.local.AuthenticationSecurityLocalDataSource
import dagger.Binds
import dagger.Module

// Tells Dagger this is a Dagger module
@Module
abstract class LocalModule {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    abstract fun provideAuthenticationSecurityLocalDataSource(
        authenticationSecurityDataSource: AuthenticationSecurityLocalDataSource
    ): AuthenticationSecurityDataSource
}
