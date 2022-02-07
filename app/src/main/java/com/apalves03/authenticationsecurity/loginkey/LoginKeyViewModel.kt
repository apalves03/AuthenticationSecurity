package com.apalves03.authenticationsecurity.loginkey

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.data.Data
import com.apalves03.authenticationsecurity.data.source.FirebaseRepository
import com.apalves03.authenticationsecurity.data.Notification
import com.apalves03.authenticationsecurity.data.SendNotification
import com.apalves03.authenticationsecurity.data.source.UserManager
import com.apalves03.authenticationsecurity.util.cancelNotifications
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Obtain information of what to show on the screen and handle complex logic.
 */
class LoginKeyViewModel @Inject constructor(
    private val userManager: UserManager,
    private val repository: FirebaseRepository
    ) : ViewModel() {

    private val _loginKeyState = MutableLiveData<LoginKeyViewState>()
    val loginKeyState: LiveData<LoginKeyViewState>
        get() = _loginKeyState

    fun loginKey(code: String, app: Application) {
        if (userManager.loginKeyUser(code)) {

            // Call cancel notification
            val notificationManager =
                ContextCompat.getSystemService(
                    app,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.cancelNotifications()

            _loginKeyState.value = LoginKeySuccess
        } else {
            _loginKeyState.value = LoginKeyError
        }
    }

    fun registerCodeKeyUser() {
        userManager.registerCodeKeyUser()
    }

    fun sendNotification(applicationContext: Context) {
        viewModelScope.launch {
            repository.sendNotification(
                createSendNotification(applicationContext)
            )
        }
    }

    private fun createSendNotification(applicationContext: Context): SendNotification {
        val notification = Notification(
            applicationContext.getString(R.string.notification_title),
            applicationContext.resources.getString(
                R.string.enter_code_body_notification, userManager.codeKeyUser
            )
        )

        val data = Data(userManager.codeKeyUser)

        return SendNotification(userManager.deviceTokenFcm, notification, data)
    }

}
