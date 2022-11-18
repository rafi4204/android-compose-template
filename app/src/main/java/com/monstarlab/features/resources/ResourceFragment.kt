package com.monstarlab.features.resources

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.monstarlab.R
import com.monstarlab.arch.extensions.launchAndRepeatWithViewLifecycle
import com.monstarlab.arch.extensions.viewBinding
import com.monstarlab.databinding.FragmentResourceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ResourceFragment : Fragment(R.layout.fragment_resource) {

    private val viewModel by viewModels<ResourceViewModel>()
    private val binding by viewBinding(FragmentResourceBinding::bind)
    private val resourceAdapter = ResourceAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.resourceRecyclerView) {
            adapter = resourceAdapter
        }
        collectFlows()
        viewModel.fetchResources()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collectFlows() {
        launchAndRepeatWithViewLifecycle(
            {
                viewModel.errorFlow.collect { errorMessage ->
                    Snackbar.make(binding.root, errorMessage.message, Snackbar.LENGTH_SHORT).show()
                }
            },
            {
                viewModel.resourcesFlow.collect { resources ->
                    resourceAdapter.updateResources(resources)
                    resourceAdapter.notifyDataSetChanged()
                }
            },
            {
                viewModel.loadingFlow.collect { loading ->
                    TransitionManager.beginDelayedTransition(binding.root)
                    binding.resourceRecyclerView.visibility =
                        if (loading) View.GONE else View.VISIBLE
                    binding.resourceProgressBar.visibility =
                        if (loading) View.VISIBLE else View.GONE
                }
            }
        )
    }
}
