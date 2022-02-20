package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.user.di.UserComponent
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
class DefaultAuthenticationSecurityRepository @Inject constructor(
    private val authenticationSecurityDataSource: AuthenticationSecurityDataSource,
    // Since UserManager will be in charge of managing the UserComponent lifecycle,
    // it needs to know how to create instances of it
    private val userComponentFactory: UserComponent.Factory
) : AuthenticationSecurityRepository {

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
        get() = authenticationSecurityDataSource.getString(REGISTERED_USER)

    val codeKeyUser: String
        get() = authenticationSecurityDataSource.getString(CODE_KEY_USER)

    val deviceTokenFcm: String
        get() = authenticationSecurityDataSource.getString(DEVICE_TOKEN_FCM)

    override fun isUserLoggedIn() = userComponent != null

    override fun isUserRegistered() = authenticationSecurityDataSource.getString(REGISTERED_USER).isNotEmpty()

    override fun registerUser(username: String, password: String) {
        authenticationSecurityDataSource.setString(REGISTERED_USER, username)
        authenticationSecurityDataSource.setString("$username$PASSWORD_SUFFIX", password)

        registerCodeKeyUser()
    }

    override fun registerDeviceTokenFcm(deviceTokenFcm: String) {
        authenticationSecurityDataSource.setString(DEVICE_TOKEN_FCM, deviceTokenFcm)
    }

    override fun registerCodeKeyUser() {
        authenticationSecurityDataSource.setString(CODE_KEY_USER, refreshCodeKeyUser())
    }

    override fun loginUser(username: String, password: String): Boolean {
        val registeredUser = this.username
        if (registeredUser != username) return false

        val registeredPassword = authenticationSecurityDataSource.getString("$username$PASSWORD_SUFFIX")
        if (registeredPassword != password) return false

        authUserLoggedIn()

        registerCodeKeyUser()

        return true
    }

    override fun loginKeyUser(code: String): Boolean {
        if (codeKeyUser != code) return false

        userJustLoggedIn()
        return true
    }

    override fun logout() {
        userLoggedAuth = false
        // When the user logs out, we remove the instance of UserComponent from memory
        userComponent = null
    }

    override fun unregister() {
        val username = authenticationSecurityDataSource.getString(REGISTERED_USER)
        authenticationSecurityDataSource.setString(REGISTERED_USER, "")
        authenticationSecurityDataSource.setString("$username$PASSWORD_SUFFIX", "")
        authenticationSecurityDataSource.setString(CODE_KEY_USER, "")
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
