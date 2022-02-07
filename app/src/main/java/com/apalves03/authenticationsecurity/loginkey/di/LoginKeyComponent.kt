package com.apalves03.authenticationsecurity.loginkey.di

import com.apalves03.authenticationsecurity.di.ActivityScope
import com.apalves03.authenticationsecurity.loginkey.LoginKeyFragment
import dagger.Subcomponent

// Scope annotation that the LoginComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
// Definition of a Dagger subcomponent
@Subcomponent(modules = [LoginKeyModule::class])
interface LoginKeyComponent {

    // Factory to create instances of LoginKeyComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginKeyComponent
    }

    // Classes that can be injected by this Component
    fun inject(fragment: LoginKeyFragment)
}
