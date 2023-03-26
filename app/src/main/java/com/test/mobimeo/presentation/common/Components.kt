package com.test.mobimeo.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.test.mobimeo.R
import com.test.mobimeo.presentation.theme.*


@Composable
fun NotConnectedComponent() {
    Box(
        modifier = Modifier
            .background(color = ColorStatusNotConnected)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.text_no_connectivity
            ),
            color = White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ConnectedComponent() {
    Box(
        modifier = Modifier
            .background(color = ColorStatusConnected)
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(
                id = R.string.text_connectivity
            ),
            color = White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


/*
Custom composable for the app bar
 */
@Composable
fun AppBarComponent(
    title: String,
    navigator: DestinationsNavigator,
    showBack: Boolean = false,
    showShare: Boolean = false, onShareClicked: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = ToolbarColor,
        elevation = 2.dp
    ) {
        if (showBack) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.text_arrow_back),
                modifier = Modifier.clickable {
                    navigator.popBackStack()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            title, textAlign = if (showBack) TextAlign.Start else TextAlign.Center,
            fontSize = 16.sp,
            color = ToolbarTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        if (showShare) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                Icons.Rounded.Share,
                contentDescription = stringResource(id = R.string.text_arrow_back),
                modifier = Modifier.clickable {
                    onShareClicked()
                },
                tint = ToolbarIconColor
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}


@Composable
fun ErrorComponent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            stringResource(id = R.string.text_error_text),
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 14.sp),
            textAlign = TextAlign.Center
        )
    }
}