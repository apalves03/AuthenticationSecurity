package com.apalves03.authenticationsecurity.welcome

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.data.source.UserDataRepository
import com.apalves03.authenticationsecurity.data.source.UserManager
import javax.inject.Inject

/**
 * Extension function to send messages
 *
 * @Inject tells Dagger how to provide instances of this type. Dagger also knows
 * that UserDataRepository and UserManager is a dependency.
 */
class WelcomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userManager: UserManager
) : ViewModel() {

    val welcomeText: String
        get() = "Hello ${userDataRepository.username}!"

    fun logout() {
        userManager.logout()
    }

}
