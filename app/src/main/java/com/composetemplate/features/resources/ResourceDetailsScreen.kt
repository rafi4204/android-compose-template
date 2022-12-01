package com.composetemplate.features.resources

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.loadingFlow
import coil.compose.rememberAsyncImagePainter
import com.composetemplate.arch.extensions.collectAsStateLifecycleAware
import com.composetemplate.core.domain.model.ResourceDetails
import com.composetemplate.core.ui.CircularProgressBar


@Composable
internal fun ResourceDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: ResourceDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    resourceId: String?
) {
    val resource = viewModel.resourceDetails.collectAsStateLifecycleAware().value
    val isLoading = viewModel.loadingFlow.collectAsStateLifecycleAware().value
    LaunchedEffect(key1 = Unit) {
        resourceId?.toInt()?.let { viewModel.getResourceDetails(it) }
       // Timber.tag("ResourceDetails!!").d(resourceDetails.name)
    }
    if (isLoading) {
        CircularProgressBar()
    }
    ResourceDetailsScreen(resource, onBackClick)
}

@Composable
fun ResourceDetailsScreen(resource: ResourceDetails, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(resource.imageUrl),
            contentDescription = resource.name,
            modifier = Modifier.fillMaxSize()
        )

    }

}