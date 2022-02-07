package com.apalves03.authenticationsecurity.registration


import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apalves03.authenticationsecurity.R
import javax.inject.Inject

private const val MAX_LENGTH = 5

/**
 * Obtain information of what to show on the screen and handle complex logic.
 */
class EnterDetailsViewModel @Inject constructor() : ViewModel() {

    private val _enterDetailsState = MutableLiveData<EnterDetailsViewState>()
    val enterDetailsState: LiveData<EnterDetailsViewState>
        get() = _enterDetailsState

    fun validateInput(username: String, password: String, fragment: Fragment) {
        when {
            username.length < MAX_LENGTH -> _enterDetailsState.value =
                EnterDetailsError(
                    fragment.getString(R.string.registration_username_error)
                )
            password.length < MAX_LENGTH -> _enterDetailsState.value =
                EnterDetailsError(
                    fragment.getString(R.string.registration_password_error)
                )
            else -> _enterDetailsState.value = EnterDetailsSuccess
        }
    }
}
