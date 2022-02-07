package com.apalves03.authenticationsecurity.registration.di

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.di.ViewModelKey
import com.apalves03.authenticationsecurity.registration.EnterDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

// Tells Dagger this is a Dagger module
@Module
abstract class EnterDetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EnterDetailsViewModel::class)
    abstract fun bindViewModel(viewmodel: EnterDetailsViewModel): ViewModel

}