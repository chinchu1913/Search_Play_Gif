package com.test.mobimeo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.test.mobimeo.presentation.trendingGiphy.NavGraphs

import com.test.mobimeo.presentation.theme.GiphyTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiphyTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}




