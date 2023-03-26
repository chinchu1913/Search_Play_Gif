package com.test.mobimeo.data.dto

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

data class GiphyDataDto(
    val data: List<GiphyDto>,
    val pagination: PaginationDto,
    val meta: MetaDto
)

data class GiphyDto(
    val id: String,
    val url: String,
    @SerializedName("bitly_url")
    val burl: String,
    val images: ImagesDto,
)

data class ImagesDto(
    @SerializedName("fixed_height")
    val fixedHeight: FixedHeightDto,
    @SerializedName("preview_gif")
    val preview: FixedHeightDto,
    val original: FixedHeightDto
)

data class FixedHeightDto(
    val url: String,
    val height: Int,
    val width: Int
)


data class PaginationDto(
    val offset: Int,
    @SerializedName("total_count")
    val totalCount: Int,
    val count: Int
)

@JsonClass(generateAdapter = true)
data class MetaDto(
    val msg: String,
    val status: Int,
    @SerializedName("response_id")
    val responseId: String
)
