package com.apalves03.authenticationsecurity.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.apalves03.authenticationsecurity.MyApplication
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.databinding.FragmentWelcomeBinding
import javax.inject.Inject

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainViewModelFactory: ViewModelProvider.Factory

    private val mainViewModel by viewModels<WelcomeViewModel> { mainViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as MyApplication).appComponent
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
        _binding = FragmentWelcomeBinding.inflate(inflater)

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
                .navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }

}
