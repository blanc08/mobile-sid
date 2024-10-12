package com.blanc08.sid.feature.place

import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.blanc08.sid.data.place.Place
import com.blanc08.sid.designsystem.theme.AppTypography
import com.blanc08.sid.designsystem.theme.primaryLight
import com.blanc08.sid.viewmodels.PlaceViewModel

const val placeMapIFrameTemplate = """
    <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </head>
        <body style="margin:0;padding:0;">
            <iframe
                width="100%"
                height="250"
                frameborder="0" style="border:0"
                referrerpolicy="no-referrer-when-downgrade"
                src="https://www.google.com/maps/embed/v1/place?key={key}
                &region=ID&q={q}&center=-6.8223604,107.1169712&zoom=13"
                allowfullscreen>
          </iframe>
        </body>
    </html>
"""

@Composable
fun PlaceDetailRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlaceViewModel = hiltViewModel(),
) {
    val place by viewModel.place.collectAsState()

    if (place == null) {
        return Text("Not Found")
    }

    PlaceDetailsScreen(
        onBackClick = onBackClick,
        place = place!!,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailsScreen(
    onBackClick: () -> Unit,
    place: Place,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState(0)

    val iFrameHtml =
        placeMapIFrameTemplate.replace("{key}", "AIzaSyChBILnLwS5iotE9suvFlmFkm4kSIFVirA")
            .replace("{q}", " Ciputri, Perkebunan teh")


    Column {
        TopAppBar(
            title = {
                Text(
                    style = AppTypography.titleLarge,
                    text = place.name
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 14.dp)
                .verticalScroll(
                    scrollState,
                )
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)), // Clip the image to match card corners
                contentScale = ContentScale.Crop,
                model = place.thumbnail,
                contentDescription = null,
            )
            Text(
                style = AppTypography.titleMedium,
                text = "Deskripsi"
            )
            HorizontalDivider(color = primaryLight)
            Text(
                text = place.description,
                style = AppTypography.bodyMedium,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                style = AppTypography.titleMedium,
                text = "Lokasi"
            )
            HorizontalDivider(color = primaryLight)
            IframeView(
                iFrameHtml,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}


@Composable
fun IframeView(htmlString: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true // Enable JavaScript
                webViewClient = WebViewClient()   // Handle page redirects inside WebView
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

                // Load HTML with iframe
                loadData(
                    htmlString.trimIndent(),
                    "text/html",
                    "UTF-8"
                )
            }
        }

    )
}
