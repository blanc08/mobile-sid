package com.blanc08.sid.feature.gallery

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.blanc08.sid.data.photo.Photo
import com.blanc08.sid.viewmodels.GalleryViewModel

const val TAG = "GalleryCompose"

@Composable
fun GalleryRoute(
    onPictureClick: (String) -> Unit,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val photos by viewModel.photos.collectAsState()

    LaunchedEffect(photos) {
        Log.d(TAG, photos.toList().toString())
    }

    GalleryScreen(
        photos = photos,
        onPictureClick = onPictureClick
    )
}

@Composable
fun GalleryScreen(
    photos: List<Photo>,
    onPictureClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(items = photos) { photo ->
            PhotoItem(photo, onPictureClick)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onPictureClick: (String) -> Unit) {
    AsyncImage(
        model = photo.url,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { onPictureClick(photo.id.toString()) },
        contentScale = ContentScale.Fit
    )
}
