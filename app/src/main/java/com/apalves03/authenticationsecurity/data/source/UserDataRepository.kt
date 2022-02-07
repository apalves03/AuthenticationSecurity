package com.apalves03.authenticationsecurity.data.source

import com.apalves03.authenticationsecurity.welcome.di.LoggedUserScope
import javax.inject.Inject

/**
 * UserDataRepository contains user-specific data such as username and unread notifications.
 *
 * This object will have a unique instance in a Component that is annotated with
 * @LoggedUserScope (i.e. only UserComponent in this case).
 */
@LoggedUserScope
class UserDataRepository @Inject constructor(private val userManager: UserManager) {

    val username: String
        get() = userManager.username

}

