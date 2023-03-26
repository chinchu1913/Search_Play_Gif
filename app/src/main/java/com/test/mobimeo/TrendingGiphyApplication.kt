package com.test.mobimeo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TrendingGiphyApplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: TrendingGiphyApplication
            private set
    }
}
