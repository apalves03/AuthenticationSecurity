package com.apalves03.authenticationsecurity.registration

import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.data.source.UserManager
import com.apalves03.authenticationsecurity.di.ActivityScope
import javax.inject.Inject

/**
 * Obtain information of what to show on the screen and handle complex logic.
 *
 * @Inject tells Dagger how to provide instances of this type. Dagger also knows
 * that UserManager is a dependency.
 */
@ActivityScope
class RegistrationViewModel @Inject constructor(val userManager: UserManager) : ViewModel() {

    private var username: String? = null
    private var password: String? = null
    private var acceptedTCs: Boolean? = null

    fun updateUserData(username: String, password: String) {
        this.username = username
        this.password = password
    }

    fun acceptTCs() {
        acceptedTCs = true
    }

    fun registerUser() {
        assert(username != null)
        assert(password != null)
        assert(acceptedTCs == true)

        userManager.registerUser(username!!, password!!)
    }

}
