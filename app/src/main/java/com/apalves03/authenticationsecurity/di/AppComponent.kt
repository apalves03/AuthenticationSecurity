package com.apalves03.authenticationsecurity.di

import android.content.Context
import com.apalves03.authenticationsecurity.data.source.DefaultAuthenticationSecurityRepository
import com.apalves03.authenticationsecurity.data.source.remote.di.RemoteModule
import com.apalves03.authenticationsecurity.login.di.LoginComponent
import com.apalves03.authenticationsecurity.loginkey.di.LoginKeyComponent
import com.apalves03.authenticationsecurity.registration.di.RegistrationComponent
import com.apalves03.authenticationsecurity.data.source.local.di.LocalModule
import com.apalves03.authenticationsecurity.user.di.UserComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

// Scope annotation that the AppComponent uses
// Classes annotated with @Singleton will have a unique instance in this Component
@Singleton
// Definition of a Dagger component that adds info from the different modules to the graph
@Component(modules = [
    LocalModule::class,
    RemoteModule::class,
    ViewModelBuilderModule::class,
    AppSubcomponents::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Types that can be retrieved from the graph
    fun registrationComponent(): RegistrationComponent.Factory
    fun loginComponent(): LoginComponent.Factory
    fun loginKeyComponent(): LoginKeyComponent.Factory
    fun userComponent(): UserComponent.Factory
    fun userManager(): DefaultAuthenticationSecurityRepository
}
