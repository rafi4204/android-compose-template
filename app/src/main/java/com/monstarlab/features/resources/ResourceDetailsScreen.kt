package com.monstarlab.features.resources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.monstarlab.R
import com.monstarlab.core.pagination.model.Resource
import com.monstarlab.core.ui.CircularProgressBar


@Composable
internal fun ResourceDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: ResourceViewModel = hiltViewModel(),
    navigateToDetails: (String) -> Unit
) {
    val resourceResult = viewModel.resourceResult.collectAsLazyPagingItems()
    ResourceScreen(resourceResult)
}

@Composable
fun ResourceScreen(resourceResult: LazyPagingItems<Resource>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                .systemBarsPadding(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(resourceResult) { item ->
                ResourceItemView(item)
            }
            resourceResult.apply {
                when {
                    loadState.refresh is LoadState.Loading -> item {
                        CircularProgressBar()
                    }

                    loadState.append is LoadState.Loading -> item {
                        CircularProgressBar()
                    }

                    itemSnapshotList.isEmpty() -> item { Text(text = stringResource(R.string.no_data_found)) }
                }
            }
        }

    }

}

@Composable
fun ResourceItemView(item: Resource?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.secondary,
                    bounded = true
                ),
                onClick = {
                    /* val bundle = Bundle()
                     bundle.putSerializable(location, locationItem)
                     navController.navigate(
                         R.id.action_nearestLocationFragment_to_locationDirectionFragment,
                         false,
                         R.id.nearestLocationFragment,
                         bundle
                     )*/
                },
            ),
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp,
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            if (item != null) {
                Text(
                    item.name,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}