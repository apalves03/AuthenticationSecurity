package com.apalves03.authenticationsecurity.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.apalves03.authenticationsecurity.AuthenticationSecurityApplication
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.databinding.FragmentTermsConditionsBinding
import javax.inject.Inject

class TermsConditionsFragment : Fragment() {

    private var _binding: FragmentTermsConditionsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var registrationViewModelFactory: ViewModelProvider.Factory

    private val registrationSharedViewModel by activityViewModels<RegistrationViewModel> { registrationViewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Grabs the registrationComponent from the Activity and injects this Fragment
        (activity?.application as AuthenticationSecurityApplication).appComponent
            .registrationComponent()
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
        _binding = FragmentTermsConditionsBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.register.setOnClickListener {
            registrationSharedViewModel.acceptTCs()

            registrationSharedViewModel.registerUser()

            findNavController()
                .navigate(R.id.action_termsConditionsFragment_to_loginKeyFragment)
        }
    }


}
