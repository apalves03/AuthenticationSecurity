package com.apalves03.authenticationsecurity.registration.di

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.di.ViewModelKey
import com.apalves03.authenticationsecurity.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Tells Dagger this is a Dagger module
@Module
abstract class RegistrationModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindViewModel(viewmodel: RegistrationViewModel): ViewModel

}