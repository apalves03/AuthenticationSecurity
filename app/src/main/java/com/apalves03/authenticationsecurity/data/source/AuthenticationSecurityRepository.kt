package com.apalves03.authenticationsecurity.data.source

/**
 * Interface to the data layer.
 */
interface AuthenticationSecurityRepository {

    fun isUserLoggedIn(): Boolean
    fun isUserRegistered(): Boolean
    fun registerUser(username: String, password: String)
    fun registerDeviceTokenFcm(deviceTokenFcm: String)
    fun registerCodeKeyUser()
    fun loginUser(username: String, password: String): Boolean
    fun loginKeyUser(code: String): Boolean
    fun logout()
    fun unregister()

}