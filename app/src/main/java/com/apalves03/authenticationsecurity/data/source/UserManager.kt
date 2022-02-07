package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.data.source.local.Storage
import com.apalves03.authenticationsecurity.welcome.di.UserComponent
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

private const val REGISTERED_USER = "registered_user"
private const val PASSWORD_SUFFIX = "password"
private const val DEVICE_TOKEN_FCM = "device_token_fcm"
private const val CODE_KEY_USER = "code_key_user"
private const val MAX_CODE_KEY_NUMBER = 999999

/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 *
 * Marked with @Singleton since we only one an instance of UserManager in the application graph.
 */
@Singleton
class UserManager @Inject constructor(
    private val storage: Storage,
    // Since UserManager will be in charge of managing the UserComponent lifecycle,
    // it needs to know how to create instances of it
    private val userComponentFactory: UserComponent.Factory
) {

    /**
    *  UserComponent is specific to a logged in user. Holds an instance of UserComponent.
    *  This determines if the user is logged in or not, when the user logs in,
    *  a new Component will be created. When the user logs out, this will be null.
    */
    var userComponent: UserComponent? = null
        private set

    var userLoggedAuth: Boolean = false
        private set

    val username: String
        get() = storage.getString(REGISTERED_USER)

    val codeKeyUser: String
        get() = storage.getString(CODE_KEY_USER)

    val deviceTokenFcm: String
        get() = storage.getString(DEVICE_TOKEN_FCM)

    fun isUserLoggedIn() = userComponent != null

    fun isUserRegistered() = storage.getString(REGISTERED_USER).isNotEmpty()

    fun registerUser(username: String, password: String) {
        storage.setString(REGISTERED_USER, username)
        storage.setString("$username$PASSWORD_SUFFIX", password)

        registerCodeKeyUser()
    }

    fun registerDeviceTokenFcm(deviceTokenFcm: String) {
        storage.setString(DEVICE_TOKEN_FCM, deviceTokenFcm)
    }

    fun registerCodeKeyUser() {
        storage.setString(CODE_KEY_USER, refreshCodeKeyUser())
    }

    fun loginUser(username: String, password: String): Boolean {
        val registeredUser = this.username
        if (registeredUser != username) return false

        val registeredPassword = storage.getString("$username$PASSWORD_SUFFIX")
        if (registeredPassword != password) return false

        authUserLoggedIn()

        registerCodeKeyUser()

        return true
    }

    fun loginKeyUser(code: String): Boolean {
        if (codeKeyUser != code) return false

        userJustLoggedIn()
        return true
    }

    fun logout() {
        userLoggedAuth = false
        // When the user logs out, we remove the instance of UserComponent from memory
        userComponent = null
    }

    fun unregister() {
        val username = storage.getString(REGISTERED_USER)
        storage.setString(REGISTERED_USER, "")
        storage.setString("$username$PASSWORD_SUFFIX", "")
        storage.setString(CODE_KEY_USER, "")
        logout()
    }

    private fun authUserLoggedIn() {
        userLoggedAuth = true
    }

    private fun userJustLoggedIn() {
        // When the user logs in, we create a new instance of UserComponent
        userComponent = userComponentFactory.create()
    }

    private fun refreshCodeKeyUser(): String {
        return "%0${4}d".format(
            Random.nextInt(until = MAX_CODE_KEY_NUMBER)
        )
    }

}
