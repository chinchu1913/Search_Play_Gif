package com.test.mobimeo.di

import android.content.Context
import com.test.mobimeo.common.utils.NetworkUtil
import com.test.mobimeo.common.utils.NetworkUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUtilModule {

    @Binds
    @Singleton
    abstract fun buildNetworkUtil(
        networkUtilImpl: NetworkUtilsImpl,
    ): NetworkUtil

}