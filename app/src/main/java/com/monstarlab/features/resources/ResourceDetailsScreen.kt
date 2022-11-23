package com.monstarlab.features.resources

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.monstarlab.arch.extensions.collectAsStateLifecycleAware
import com.monstarlab.core.domain.model.ResourceDetails


@Composable
internal fun ResourceDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: ResourceDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    resourceId: String?
) {
    val resource = viewModel.resourceDetails.collectAsStateLifecycleAware().value
    resourceId?.toInt()?.let { viewModel.getResourceDetails(it) }
    ResourceDetailsScreen(resource, onBackClick)
    LaunchedEffect(key1 = Unit ){

    }
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