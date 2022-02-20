package com.apalves03.authenticationsecurity.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.apalves03.authenticationsecurity.AuthenticationSecurityApplication
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.databinding.FragmentUserBinding
import javax.inject.Inject

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: ViewModelProvider.Factory

    private val mainViewModel by viewModels<UserViewModel> { mainViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as AuthenticationSecurityApplication).appComponent
            .userComponent()
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
        _binding = FragmentUserBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        binding.hello.text = mainViewModel.welcomeText

        binding.logout.setOnClickListener {
            mainViewModel.logout()

            findNavController()
                .navigate(R.id.action_userFragment_to_loginFragment)
        }
    }

}
