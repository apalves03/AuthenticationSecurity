package com.apalves03.authenticationsecurity.loginkey

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.apalves03.authenticationsecurity.AuthenticationSecurityApplication
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.databinding.FragmentLoginKeyBinding
import com.apalves03.authenticationsecurity.util.CODE_EXTRA
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import javax.inject.Inject

class LoginKeyFragment : Fragment() {

    private var _binding: FragmentLoginKeyBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var loginKeyViewModelFactory: ViewModelProvider.Factory

    private val loginKeyViewModel by viewModels<LoginKeyViewModel> { loginKeyViewModelFactory }

    private var ignoreExtraCodeKey = true

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as AuthenticationSecurityApplication).appComponent
            .loginKeyComponent()
            .create()
            .inject(this)
    }

    /**
     * Inflates the layout with Data Binding.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginKeyBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        loginKeyViewModel.loginKeyViewState.observe(viewLifecycleOwner, Observer<LoginKeyViewState> { state ->
            when (state) {
                is LoginKeySuccess -> {
                    findNavController()
                        .navigate(R.id.action_loginKeyFragment_to_userFragment)
                }
                is LoginKeyError -> binding.error.visibility = View.VISIBLE
            }
        })

        setupViews()
    }

    override fun onResume() {
        super.onResume()

        if (!ignoreExtraCodeKey) {
            getExtrasNotification(requireActivity().intent)
        }

        ignoreExtraCodeKey = false

    }

    private fun getExtrasNotification(intent: Intent?) {
        if (activity == null || (activity?.isFinishing as Boolean)) {
            return
        }

        intent?.extras.let {
            if (it != null && it.containsKey(CODE_EXTRA)) {
                binding.code.setText(it.getString(CODE_EXTRA))
            }
        }
    }

    private fun setupViews() {
        binding.code.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        binding.validate.setOnClickListener {
            loginKeyViewModel.loginKey(binding.code.text.toString(), (activity?.application as AuthenticationSecurityApplication))
        }

        binding.resendCode.setOnClickListener {
            resendCode()
        }

        // call create channel
        createChannel(
            getString(R.string.app_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )

        // Create a new channel for FCM
        createChannel(
            getString(R.string.autentication_notification_channel_id),
            getString(R.string.autentication_notification_channel_name)
        )

        sendNotification()
    }

    private fun resendCode() {
        // refresh code
        loginKeyViewModel.registerCodeKeyUser()

        //send code by notification
        sendNotification()
    }

    private fun sendNotification() {
        if (activity == null || (activity?.isFinishing as Boolean)) {
            return
        }

        loginKeyViewModel.sendNotification((activity?.application as AuthenticationSecurityApplication).applicationContext)
    }

    private fun createChannel(channelId: String, channelName: String) {
        // START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                // Disable badges for this channel
                .apply {
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.autentication_notification_channel_description)

            val notificationManager = (activity?.application as AuthenticationSecurityApplication).getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
        // END create channel
    }

}

sealed class LoginKeyViewState
object LoginKeySuccess : LoginKeyViewState()
object LoginKeyError : LoginKeyViewState()
