package com.test.mobimeo.presentation.trendingGiphy


sealed class HomeScreenEvent {
    object GetGiphyList : HomeScreenEvent()
    object GetGiphyListMore : HomeScreenEvent()
}