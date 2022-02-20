package com.apalves03.authenticationsecurity.user

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.data.source.DefaultAuthenticationSecurityRepository
import javax.inject.Inject

/**
 * Extension function to send messages
 *
 * @Inject tells Dagger how to provide instances of this type. Dagger also knows
 * that UserDataRepository and UserManager is a dependency.
 */
class UserViewModel @Inject constructor(
    private val defaultAuthenticationSecurityRepository: DefaultAuthenticationSecurityRepository
) : ViewModel() {

    val welcomeText: String
        get() = "Hello ${defaultAuthenticationSecurityRepository.username}!"

    fun logout() {
        defaultAuthenticationSecurityRepository.logout()
    }

}
