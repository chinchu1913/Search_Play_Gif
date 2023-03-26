package com.test.mobimeo.data.repository

import com.test.mobimeo.common.Resource
import com.test.mobimeo.data.GiphyApi
import com.test.mobimeo.domain.entities.GiphyData
import com.test.mobimeo.domain.entities.toGiphyData
import com.test.mobimeo.domain.repository.GiphyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepositoryImpl @Inject constructor(
    private val api: GiphyApi,
) : GiphyRepository {


    override suspend fun getTrendingGiphy(offset: Int, limit: Int): Flow<Resource<GiphyData>> {
        return flow {
            emit(value = Resource.Loading(true))

            val remoteData = try {
                val response = api.getTrendingGiphy(
                    offset = offset, limit = limit,
                )
                response
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                null
            }

            remoteData?.let { data ->
                emit(
                    Resource.Success(
                        data = data.toGiphyData()
                    )
                )
                emit(
                    Resource.Loading(
                        isLoading = false
                    )
                )
                return@flow
            }

        }
    }

}
