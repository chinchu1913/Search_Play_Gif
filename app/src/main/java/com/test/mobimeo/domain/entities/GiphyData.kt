package com.test.mobimeo.domain.entities

import com.test.mobimeo.data.dto.*

data class GiphyData(
    val data: List<Giphy>,
    val pagination: Pagination,
    val meta: Meta
)

data class Giphy(
    val id: String,
    val url: String,
    val burl: String,
    val images: Images,
)

data class Images(
    val fixedHeight: FixedHeight,
    val preview: FixedHeight,
    val original: FixedHeight
)

data class FixedHeight(
    val url: String,
    val height: Int,
    val width: Int
)

data class Pagination(
    val offset: Int,
    val totalCount: Int,
    val count: Int
)

data class Meta(
    val msg: String,
    val status: Int,
    val responseId: String
)


fun GiphyDataDto.toGiphyData(): GiphyData {
    return GiphyData(
        data = data.map { it.toGiphy() },
        meta = meta.toMeta(),
        pagination = pagination.toPagination()
    )
}

fun GiphyDto.toGiphy(): Giphy {
    return Giphy(id = id, url = url, burl = burl, images = images.toImage())
}

fun MetaDto.toMeta(): Meta {
    return Meta(status = status, responseId = responseId, msg = msg)
}

fun ImagesDto.toImage(): Images {
    return Images(
        fixedHeight = fixedHeight.toFixedHeight(), preview = preview.toFixedHeight(),
        original = original.toFixedHeight()
    )
}

fun FixedHeightDto.toFixedHeight(): FixedHeight {
    return FixedHeight(url = url, height = height, width = width)
}

fun PaginationDto.toPagination(): Pagination {
    return Pagination(offset = offset, totalCount = totalCount, count = count)
}