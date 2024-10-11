package com.blanc08.sid.feature.gallery

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.blanc08.sid.data.photo.Photo
import com.blanc08.sid.viewmodels.GalleryViewModel


@Composable
fun GalleryRoute(
    onPictureClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val photos by viewModel.photos.collectAsState()

    GalleryScreen(
        photos = photos,
        modifier = modifier
    )
}

@Composable
fun GalleryScreen(
    photos: List<Photo>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(items = photos) { photo ->
            PhotoItem(photo)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo) {
    AsyncImage(
        model = photo.url,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Fit
    )
}