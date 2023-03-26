package com.test.mobimeo.data.repository

import com.google.common.truth.Truth.assertThat
import com.test.mobimeo.common.Resource
import com.test.mobimeo.data.GiphyApi
import com.test.mobimeo.data.dto.*
import com.test.mobimeo.domain.entities.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class GiphyRepositoryImplTest {

    @Mock
    private lateinit var mockApi: GiphyApi

    private lateinit var repository: GiphyRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = GiphyRepositoryImpl(mockApi)
    }

    @Test
    fun `getTrendingGiphy returns Success`() = runBlocking {
        // Given
        val expectedData = getGiphyData()
        val expectedResponse = getGiphyDataDto()

        `when`(mockApi.getTrendingGiphy(0, 20)).thenReturn(expectedResponse)


        // When
        val flow: Flow<Resource<GiphyData>> = repository.getTrendingGiphy(0, 20)
        val actualList = mutableListOf<Resource<GiphyData>>()
        flow.collect {
            actualList.add(it)
        }

        // Then
        assertThat((actualList[0] as Resource.Loading).isLoading).isEqualTo(true)
        assertThat(actualList[1].data?.data?.first()?.id)
            .isEqualTo(expectedData.data.first().id)
        assertThat(actualList[1].data?.data?.first()?.burl)
            .isEqualTo(expectedData.data.first().burl)
        assertThat(actualList[1].data?.data?.first()?.url)
            .isEqualTo(expectedData.data.first().url)
    }

    @Test
    fun `getTrendingGiphy returns Error`() = runBlocking {
        // Given
        val expectedErrorMessage = "Couldn't load data"
        val expectedException = HttpException(
            Response.error<Any>(
                404,
                expectedErrorMessage.toResponseBody("text/plain".toMediaTypeOrNull())
            )
        )
        `when`(mockApi.getTrendingGiphy(0, 20)).thenThrow(expectedException)

        // When
        val flow: Flow<Resource<GiphyData>> = repository.getTrendingGiphy(0, 20)
        val actualList = mutableListOf<Resource<GiphyData>>()
        flow.collect {
            actualList.add(it)
        }

        // Then
        assertThat((actualList[0] as Resource.Loading).isLoading).isEqualTo(true)
        assertThat((actualList[1].message)).isEqualTo(expectedErrorMessage)

    }

    private fun getGiphyData(): GiphyData {
        val images = Images(
            FixedHeight("https://media1.giphy.com/media/1234/giphy.gif", 200, 200),
            FixedHeight("https://media1.giphy.com/media/1234/giphy.gif", 200, 200),
            FixedHeight("https://media1.giphy.com/media/1234/giphy.gif", 200, 200)
        )
        val giphy = Giphy("1234", "https://giphy.com/1234", "https://bit.ly/1234", images)
        val pagination = Pagination(0, 1, 1)
        val meta = Meta("Success", 200, "responseId")
        return GiphyData(listOf(giphy), pagination, meta)
    }

    private fun getGiphyDataDto() = GiphyDataDto(
        listOf(
            GiphyDto(
                "1234",
                "https://giphy.com/1234",
                "https://bit.ly/1234",
                ImagesDto(
                    FixedHeightDto(
                        "https://media1.giphy.com/media/1234/giphy.gif",
                        200,
                        200
                    ),
                    FixedHeightDto(
                        "https://media1.giphy.com/media/1234/giphy.gif",
                        200,
                        200
                    ),
                    FixedHeightDto(
                        "https://media1.giphy.com/media/1234/giphy.gif",
                        200,
                        200
                    )
                )
            )
        ),
        PaginationDto(0, 1, 1),
        MetaDto("Success", 200, "responseId")
    )

}
