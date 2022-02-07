package com.apalves03.authenticationsecurity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.data.source.UserManager
import javax.inject.Inject

/**
 * Obtain information of what to show on the screen and handle complex logic.
 */
class LoginViewModel @Inject constructor(private val userManager: UserManager) : ViewModel() {

    private val _loginState = MutableLiveData<LoginViewState>()
    val loginState: LiveData<LoginViewState>
        get() = _loginState

    fun login(username: String, password: String) {
        if (userManager.loginUser(username, password)) {
            _loginState.value = LoginSuccess
        } else {
            _loginState.value = LoginError
        }
    }

    fun unregister() {
        userManager.unregister()
    }

    fun getUsername(): String = userManager.username
}
