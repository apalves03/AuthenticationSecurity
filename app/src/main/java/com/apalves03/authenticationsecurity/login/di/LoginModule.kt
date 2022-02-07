package com.apalves03.authenticationsecurity.login.di

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.di.ViewModelKey
import com.apalves03.authenticationsecurity.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Tells Dagger this is a Dagger module
@Module
abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel

}