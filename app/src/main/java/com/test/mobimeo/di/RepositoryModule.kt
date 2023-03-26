package com.test.mobimeo.di

import com.test.mobimeo.data.repository.GiphyRepositoryImpl
import com.test.mobimeo.domain.repository.GiphyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun buildArticleRepository(
        giphyRepositoryImpl: GiphyRepositoryImpl,
    ): GiphyRepository
}