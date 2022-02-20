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
import com.apalves03.authenticationsecurity.data.source.DefaultPushNotificationRepository
import com.apalves03.authenticationsecurity.data.Notification
import com.apalves03.authenticationsecurity.data.SendNotification
import com.apalves03.authenticationsecurity.data.source.DefaultAuthenticationSecurityRepository
import com.apalves03.authenticationsecurity.util.cancelNotifications
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Obtain information of what to show on the screen and handle complex logic.
 */
class LoginKeyViewModel @Inject constructor(
    private val defaultAuthenticationSecurityRepository: DefaultAuthenticationSecurityRepository,
    private val repositoryDefault: DefaultPushNotificationRepository
    ) : ViewModel() {

    private val _loginKeyViewState = MutableLiveData<LoginKeyViewState>()
    val loginKeyViewState: LiveData<LoginKeyViewState>
        get() = _loginKeyViewState

    fun loginKey(code: String, app: Application) {
        if (defaultAuthenticationSecurityRepository.loginKeyUser(code)) {

            // Call cancel notification
            val notificationManager =
                ContextCompat.getSystemService(
                    app,
                    NotificationManager::class.java
                ) as NotificationManager
            notificationManager.cancelNotifications()

            _loginKeyViewState.value = LoginKeySuccess
        } else {
            _loginKeyViewState.value = LoginKeyError
        }
    }

    fun registerCodeKeyUser() {
        defaultAuthenticationSecurityRepository.registerCodeKeyUser()
    }

    fun sendNotification(applicationContext: Context) {
        viewModelScope.launch {
            repositoryDefault.sendNotification(
                createSendNotification(applicationContext)
            )
        }
    }

    private fun createSendNotification(applicationContext: Context): SendNotification {
        val notification = Notification(
            applicationContext.getString(R.string.notification_title),
            applicationContext.resources.getString(
                R.string.enter_code_body_notification, defaultAuthenticationSecurityRepository.codeKeyUser
            )
        )

        val data = Data(defaultAuthenticationSecurityRepository.codeKeyUser)

        return SendNotification(defaultAuthenticationSecurityRepository.deviceTokenFcm, notification, data)
    }

}
