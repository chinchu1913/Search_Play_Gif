package com.test.mobimeo.domain.repository

import com.test.mobimeo.common.Resource
import com.test.mobimeo.domain.entities.GiphyData
import kotlinx.coroutines.flow.Flow

interface GiphyRepository {
    suspend fun getTrendingGiphy(offset: Int, limit: Int): Flow<Resource<GiphyData>>
}