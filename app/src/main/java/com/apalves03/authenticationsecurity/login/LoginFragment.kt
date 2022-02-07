package com.apalves03.authenticationsecurity.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.apalves03.authenticationsecurity.MyApplication
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.apalves03.authenticationsecurity.AuthenticationSecurityActivity
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.databinding.FragmentLoginBinding
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var loginViewModelFactory: ViewModelProvider.Factory

    private val loginViewModel by viewModels<LoginViewModel> { loginViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as MyApplication).appComponent
            .loginComponent()
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
        _binding = FragmentLoginBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userManager = (activity?.application as MyApplication).appComponent.userManager()
        if (!userManager.isUserLoggedIn() && !userManager.isUserRegistered()) {
            findNavController()
                .navigate(R.id.action_loginFragment_to_registrationFragment)
            return
        }

        loginViewModel.loginState.observe((activity as AuthenticationSecurityActivity), Observer<LoginViewState> { state ->
            when (state) {
                is LoginSuccess -> {
                    findNavController()
                        .navigate(R.id.action_loginFragment_to_loginKeyFragment)
                }
                is LoginError -> binding.error.visibility = View.VISIBLE
            }
        })

        setupViews()
    }

    private fun setupViews() {
        val usernameEditText = binding.username
        usernameEditText.isEnabled = false
        usernameEditText.setText(loginViewModel.getUsername())

        val passwordEditText = binding.password
        passwordEditText.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        binding.login.setOnClickListener {
            loginViewModel.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }
        binding.unregister.setOnClickListener {
            loginViewModel.unregister()
            findNavController()
                .navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

}

sealed class LoginViewState
object LoginSuccess : LoginViewState()
object LoginError : LoginViewState()
