package com.test.mobimeo.presentation.trendingGiphy

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.mobimeo.R
import com.test.mobimeo.domain.entities.Giphy
import com.test.mobimeo.presentation.common.AppBarComponent
import com.test.mobimeo.presentation.common.ConnectedComponent
import com.test.mobimeo.presentation.common.ErrorComponent
import com.test.mobimeo.presentation.common.NotConnectedComponent
import com.test.mobimeo.presentation.theme.AppBg
import com.test.mobimeo.presentation.trendingGiphy.destinations.GiphyDetailsScreenDestination
import com.test.mobimeo.presentation.trendingGiphy.viewmodel.HomeScreenViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val isRefreshing = SwipeRefreshState(isRefreshing = state.isLoading)

    Scaffold(
        modifier = Modifier,
        isFloatingActionButtonDocked = true,
        backgroundColor = AppBg,
        topBar = {
            AppBarComponent(
                title = stringResource(id = R.string.app_name),
                navigator = navigator
            )
        },
        content = { paddingValues ->
            SwipeRefresh(
                state = isRefreshing,
                onRefresh = {
                    viewModel.onEvent(HomeScreenEvent.GetGiphyList)
                },
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                GiphyListingComponent(
                    itemList = state.giphyList,
                    onItemClicked = {
                        navigator.navigate(
                            GiphyDetailsScreenDestination(
                                url = it.images.original.url, preview = it.images.preview.url
                            )
                        )

                    },
                    onScrollToEnd = {
                        viewModel.onEvent(HomeScreenEvent.GetGiphyListMore)
                    }
                )


                if (state.showNetworkUnavailable) {
                    NotConnectedComponent()
                }

                if (state.showNetworkConnected) {
                    ConnectedComponent()
                }

                if (state.isError) {
                    ErrorComponent()
                }
            }
        }
    )
}

@Composable
fun GiphyListingComponent(
    itemList: List<Giphy>,
    onItemClicked: (Giphy) -> Unit,
    onScrollToEnd: () -> Unit
) {
    val configuration = LocalConfiguration.current

    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val lazyListState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyListState,
        columns = GridCells.Fixed(if (isPortrait) 2 else 3),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(itemList.size) { i ->
            val giphy = itemList[i]
            Box(modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)) {
                GiphyListItem(
                    giphy,
                    onItemClicked = onItemClicked
                )
            }

            // Check if we've scrolled to the end of the list
            if (i == itemList.size - 1 && lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == i) {
                onScrollToEnd()
            }
        }
    }
}






