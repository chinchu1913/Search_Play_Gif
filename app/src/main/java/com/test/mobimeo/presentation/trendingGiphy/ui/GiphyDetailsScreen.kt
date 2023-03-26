package com.test.mobimeo.presentation.trendingGiphy

import android.content.Intent
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.mobimeo.R
import com.test.mobimeo.presentation.common.AppBarComponent
import com.test.mobimeo.presentation.theme.AppBg

@Destination
@Composable
fun GiphyDetailsScreen(
    navigator: DestinationsNavigator,
    url: String,
    preview: String
) {
    // Local variable to keep track of whether the high quality image is loaded or not
    var isHighQualityImageLoaded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    // Load the low quality image as a preview
    val previewPainter = rememberAsyncImagePainter(
        preview,
        imageLoader = imageLoader
    )

    // Load the high quality image
    val highQualityPainter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(data = url).apply {
                size(Size.ORIGINAL)
            }
            .build(),
        imageLoader = imageLoader,
        onSuccess = {
            // Once the high quality image is loaded, update the local state variable
            isHighQualityImageLoaded = true
        }
    )

    Scaffold(
        modifier = Modifier,
        isFloatingActionButtonDocked = true,
        backgroundColor = AppBg,
        topBar = {
            AppBarComponent(
                title = stringResource(id = R.string.app_name),
                navigator = navigator, showBack = true, showShare = true, onShareClicked = {

                    val shareIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, url)
                        type = "text/plain"
                    }

                    context.startActivity(Intent.createChooser(shareIntent, "Share GIF"))


                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (!isHighQualityImageLoaded) {
                    Image(
                        painter = previewPainter,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Once the high quality image is loaded, replace the preview with the high quality image
                Image(
                    painter = highQualityPainter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )
}




