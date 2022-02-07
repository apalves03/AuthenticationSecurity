package com.apalves03.authenticationsecurity.loginkey.di

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.di.ViewModelKey
import com.apalves03.authenticationsecurity.loginkey.LoginKeyViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Tells Dagger this is a Dagger module
@Module
abstract class LoginKeyModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginKeyViewModel::class)
    abstract fun bindViewModel(viewmodel: LoginKeyViewModel): ViewModel

}