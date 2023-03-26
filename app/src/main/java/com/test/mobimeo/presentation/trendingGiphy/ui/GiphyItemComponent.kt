package com.test.mobimeo.presentation.trendingGiphy

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.test.mobimeo.domain.entities.Giphy


@Composable
fun GiphyListItem(giphyList: Giphy, onItemClicked: (Giphy) -> Unit) {
    val context = LocalContext.current
    Card(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .clickable {
                    onItemClicked(giphyList)
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp)
            ) {
                Card(modifier = Modifier.background(Color.White)) {
                    Box(
                        modifier = Modifier.height(200.dp)
                    ) {
                        val imageLoader = ImageLoader.Builder(context)
                            .components {
                                if (SDK_INT >= 28) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }
                            .build()

                        val painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context)
                                .data(data = giphyList.images.fixedHeight.url).apply(block = {
                                    size(Size.ORIGINAL)
                                }).build(), imageLoader = imageLoader
                        )
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )


                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}





