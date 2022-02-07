package com.apalves03.authenticationsecurity.registration.di

import com.apalves03.authenticationsecurity.AuthenticationSecurityActivity
import com.apalves03.authenticationsecurity.di.ActivityScope
import com.apalves03.authenticationsecurity.registration.RegistrationFragment
import com.apalves03.authenticationsecurity.registration.TermsConditionsFragment
import dagger.Subcomponent

// Scope annotation that the RegistrationComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
// Definition of a Dagger subcomponent
@Subcomponent(modules = [RegistrationModule::class, EnterDetailsModule::class])
interface RegistrationComponent {

    // Factory to create instances of RegistrationComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: AuthenticationSecurityActivity)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: TermsConditionsFragment)
}
