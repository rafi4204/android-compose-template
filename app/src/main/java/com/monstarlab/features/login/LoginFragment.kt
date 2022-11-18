package com.monstarlab.features.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.loadingFlow
import androidx.lifecycle.viewErrorFlow
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.monstarlab.R
import com.monstarlab.arch.extensions.*
import com.monstarlab.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvent()
        collectFlows()
    }

    private fun bindEvent() {
        binding.loginButton.onClick {
            viewModel.login(
                binding.loginEmailEditText.text.toString(),
                binding.loginPasswordEditText.text.toString()
            )
        }
    }

    private fun collectFlows() {
        repeatWithViewLifecycle {
            launch {
                viewModel.loginResultFlow.collect {
                    findNavController().navigate(R.id.resourceFragment)
                }
            }

            launch {
                viewModel.loadingFlow.collect { loading ->
                    TransitionManager.beginDelayedTransition(binding.root)
                    binding.loginEmailEditText.isEnabled = !loading
                    binding.loginPasswordEditText.isEnabled = !loading
                    binding.loginButton.isVisible = !loading
                }
            }
        }

        snackErrorFlow(viewModel.viewErrorFlow, binding.root)
        visibilityFlow(viewModel.loadingFlow, binding.loginProgressBar)
    }
}
