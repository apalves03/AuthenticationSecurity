package com.apalves03.authenticationsecurity.di

import com.apalves03.authenticationsecurity.login.di.LoginComponent
import com.apalves03.authenticationsecurity.loginkey.di.LoginKeyComponent
import com.apalves03.authenticationsecurity.registration.di.RegistrationComponent
import com.apalves03.authenticationsecurity.welcome.di.UserComponent
import dagger.Module

// This module tells a Component which are its subcomponents
@Module(
    subcomponents = [
        RegistrationComponent::class,
        LoginComponent::class,
        UserComponent::class,
        LoginKeyComponent::class
    ]
)
class AppSubcomponents
