package com.apalves03.authenticationsecurity.data.source

/**
 * Main entry point for saving app data.
 */
interface AuthenticationSecurityDataSource {
    fun setString(key: String, value: String)
    fun getString(key: String): String
}