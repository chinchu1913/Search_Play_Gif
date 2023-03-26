package com.test.mobimeo.presentation.trendingGiphy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.test.mobimeo.common.Resource
import com.test.mobimeo.common.utils.NetworkUtil
import com.test.mobimeo.domain.repository.GiphyRepository
import com.test.mobimeo.presentation.trendingGiphy.HomeScreenEvent
import com.test.mobimeo.presentation.trendingGiphy.HomeScreenState
import com.test.mobimeo.presentation.trendingGiphy.viewmodel.HomeScreenViewModel.ViewModelConstants.FETCH_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: GiphyRepository,
    private val networkUtils: NetworkUtil
) :
    ViewModel() {

    var state by mutableStateOf(HomeScreenState())
        private set

    private var lastConnectionStatus = true

    private var offset: Int = 0

    init {
        viewModelScope.launch {
            getGiphy()
            observeNetworkConnection()
        }
    }


    private suspend fun observeNetworkConnection() {
        networkUtils.getNetworkLiveData().asFlow().collect { isConnected ->
            //show the connection error is the connection status is disconnected
            state = state.copy(showNetworkUnavailable = !isConnected)
            //show the connection success message if the connection if disconnected and reconnected back.
            val isConnectionIsBack = !lastConnectionStatus && isConnected
            if (isConnectionIsBack) {
                coroutineScope {
                    //Refresh the data once the connection is back
                    getGiphy()
                    state = state.copy(showNetworkConnected = true)
                    delay(ViewModelConstants.CONNECTION_BACK_MSG_TIMEOUT)
                    state = state.copy(showNetworkConnected = false)
                }
            }
            lastConnectionStatus = isConnected
        }
    }

    /*
    Method to get the Giphy from the repository notify the states
     */
    private fun getGiphy() {
        viewModelScope.launch {
            repository
                .getTrendingGiphy(offset, FETCH_LIMIT)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                state = state.copy(
                                    isError = false,
                                    giphyList = if (offset == 0) {
                                        listings.data
                                    } else {
                                        state.giphyList + listings.data
                                    }
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(isError = true)
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }


    /*
    Method to handle the  events from the UI
     */
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.GetGiphyList -> {
                offset = 0
                getGiphy()
            }
            is HomeScreenEvent.GetGiphyListMore -> {
                offset += FETCH_LIMIT
                getGiphy()
            }
        }
    }


    object ViewModelConstants {
        const val CONNECTION_BACK_MSG_TIMEOUT = 2000L
        const val FETCH_LIMIT = 10
    }
}