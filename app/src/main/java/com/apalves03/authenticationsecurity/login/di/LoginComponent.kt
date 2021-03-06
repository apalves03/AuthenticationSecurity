package com.apalves03.authenticationsecurity.login.di

import com.apalves03.authenticationsecurity.di.ActivityScope
import com.apalves03.authenticationsecurity.login.LoginFragment
import dagger.Subcomponent

// Scope annotation that the LoginComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
// Definition of a Dagger subcomponent
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    // Factory to create instances of LoginComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    // Classes that can be injected by this Component
    fun inject(fragment: LoginFragment)
}
