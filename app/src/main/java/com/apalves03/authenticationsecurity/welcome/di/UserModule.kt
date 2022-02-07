package com.apalves03.authenticationsecurity.welcome.di

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.di.ViewModelKey
import com.apalves03.authenticationsecurity.welcome.WelcomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Tells Dagger this is a Dagger module
@Module
abstract class UserModule {

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun bindViewModel(viewmodel: WelcomeViewModel): ViewModel

}