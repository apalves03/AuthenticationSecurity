package com.apalves03.authenticationsecurity.registration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.apalves03.authenticationsecurity.AuthenticationSecurityApplication
import com.apalves03.authenticationsecurity.R
import com.apalves03.authenticationsecurity.databinding.FragmentRegistrationBinding
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var registrationViewModelFactory: ViewModelProvider.Factory

    private val registrationSharedViewModel by activityViewModels<RegistrationViewModel> { registrationViewModelFactory }

    @Inject
    lateinit var enterDetailsViewModelFactory: ViewModelProvider.Factory

    private val enterDetailsViewModel by viewModels<EnterDetailsViewModel> { enterDetailsViewModelFactory }

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
        _binding = FragmentRegistrationBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        enterDetailsViewModel.enterDetailsState.observe(
            viewLifecycleOwner,
            { state: EnterDetailsViewState ->
                when (state) {
                    is EnterDetailsSuccess -> {

                        val username = binding.username.text.toString()
                        val password = binding.password.text.toString()
                        registrationSharedViewModel.updateUserData(username, password)

                        findNavController()
                            .navigate(R.id.action_registrationFragment_to_termsConditionsFragment)

                    }
                    is EnterDetailsError -> {
                        binding.error.text = state.error
                        binding.error.visibility = View.VISIBLE
                    }
                }
            }
        )

        setupViews(view)
    }

    private fun setupViews(view: View) {
        binding.username.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        binding.password.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        binding.next.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            enterDetailsViewModel.validateInput(username, password, this)
        }

        binding.cancel.setOnClickListener {
            activity?.finish()
        }
    }

}

sealed class EnterDetailsViewState
object EnterDetailsSuccess : EnterDetailsViewState()
data class EnterDetailsError(val error: String) : EnterDetailsViewState()