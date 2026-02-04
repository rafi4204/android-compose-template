package com.composetemplate.features.resources

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.composetemplate.core.domain.model.ResourceDetails
import com.composetemplate.core.pagination.model.Resource
import com.composetemplate.core.ui.CircularProgressBar


@Composable
internal fun ResourceRoute(
    modifier: Modifier = Modifier,
    viewModel: ResourceViewModel = hiltViewModel(),
    onItemClick: (Int) -> Unit,
    navController: NavController
) {
    val resourceResult = viewModel.resourceResult.collectAsLazyPagingItems()
    ResourceScreen(resourceResult, onItemClick, navController)
}

@Composable
fun ResourceScreen(
    resourceResult: LazyPagingItems<Resource>,
    onItemClick: (Int) -> Unit,
    navController: NavController
) {
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
            items(resourceResult.itemCount) { index ->
                val item = resourceResult[index]
                if(item!=null) {
                    ResourceItemView(item, onItemClick, navController)
                }
            }
            resourceResult.apply {
                when {
                    loadState.refresh is LoadState.Loading -> item {
                        CircularProgressBar()
                    }

                    loadState.append is LoadState.Loading -> item {
                        CircularProgressBar()
                    }

                    itemSnapshotList.isEmpty() -> item { Text(text = stringResource(com.composetemplate.R.string.no_data_found)) }
                }
            }
        }

    }

}

@Composable
fun ResourceItemView(item: Resource?, onItemClick: (Int) -> Unit, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    color = MaterialTheme.colorScheme.secondary,
                    bounded = true
                ),
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "resourceDetails",
                        value = item?.id?.let { ResourceDetails(it, item.name, item.url,"") }
                    )
                    item?.id?.let { onItemClick(it) }
                },
            ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
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