package com.test.mobimeo.data

import com.test.mobimeo.BuildConfig
import com.test.mobimeo.data.dto.GiphyDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("/v1/gifs/trending?api_key=${BuildConfig.API_KEY}")
    suspend fun getTrendingGiphy(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): GiphyDataDto
}