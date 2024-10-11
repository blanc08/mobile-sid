package com.blanc08.sid.feature.gallery

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.blanc08.sid.data.place.Photo
import com.blanc08.sid.viewmodels.GalleryViewModel


@Composable
fun GalleryRoute(
    onPictureClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel(),
) {
    val pictures by viewModel.pictures.collectAsState()

    GalleryScreen(
        pictures = pictures,
        modifier = modifier
    )
}

@Composable
fun GalleryScreen(
    pictures: List<Photo>,
    modifier: Modifier = Modifier
) {
    Text("OK")
}