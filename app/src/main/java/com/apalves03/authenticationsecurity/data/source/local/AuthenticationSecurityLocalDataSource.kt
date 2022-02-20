package com.apalves03.authenticationsecurity.data.source.local

import android.content.Context
import com.apalves03.authenticationsecurity.data.source.AuthenticationSecurityDataSource
import javax.inject.Inject

// @Inject tells Dagger how to provide instances of this type
class AuthenticationSecurityLocalDataSource @Inject constructor(
    context: Context
) : AuthenticationSecurityDataSource {

    private val sharedPreferences = context.getSharedPreferences("AuthenticationSecurity", Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }
}
