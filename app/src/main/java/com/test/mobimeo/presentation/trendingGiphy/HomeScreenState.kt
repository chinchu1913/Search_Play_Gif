package com.test.mobimeo.presentation.trendingGiphy

import com.test.mobimeo.domain.entities.Giphy


data class HomeScreenState(
    val giphyList: List<Giphy> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val showNetworkUnavailable: Boolean = false,
    val showNetworkConnected: Boolean = false,
)
