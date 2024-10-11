package com.blanc08.sid.feature.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.blanc08.sid.data.photo.Photo
import com.blanc08.sid.viewmodels.PhotoDetailViewModel


@Composable
fun PhotoDetailRoute(
    onBackClick: () -> Unit,
    viewModel: PhotoDetailViewModel = hiltViewModel(),
) {
    val photo by viewModel.photo.collectAsState()

    if (photo == null) return Text("Not Found")

    PhotoDetailsScreen(
        onBackClick = onBackClick,
        photo = photo as Photo,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreen(
    onBackClick: () -> Unit,
    photo: Photo,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Detail Photo")
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
        Row(modifier = Modifier.padding(5.dp)) {
            AsyncImage(
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)), // Clip the image to match card corners
                contentScale = ContentScale.Crop,
                model = photo.url,
                contentDescription = null,
            )
        }
        Text(text = photo.description)
    }
}