package com.blanc08.sid.compose.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.blanc08.sid.data.place.Place
import com.blanc08.sid.ui.theme.AppTheme
import com.blanc08.sid.viewmodels.PlaceListViewModel

private const val buffer = 1;

@Composable
fun HomeScreen(
    onCardClick: (Place) -> Unit,
    placeListViewModel: PlaceListViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()

    val places by placeListViewModel.places.collectAsState()

    // observe list scrolling
    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer
        }
    }

    // load more if scrolled to bottom
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) placeListViewModel.loadPlaces()
    }

    LaunchedEffect(places) {
        Log.d("Home Screen", "places" + places.toList())
    }


    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Header()
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(items = places, key = { place -> place.id }) { place ->
                    PlaceCard(place, onCardClick = {
                        onCardClick(place)
                    })
                }
            }
        )
    }
}


@Composable
fun Header() {
    Surface(
        tonalElevation = 5.dp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GreetingText()
                UtilIcons()
            }
        }
    }
}

@Composable
fun UtilIcons() {
    IconButton(
        onClick = {},
        modifier = Modifier
            .size(20.dp)
    ) {
        Icon(
            Icons.Filled.DarkMode,
            contentDescription = "Toggle Dark Mode",
            tint = MaterialTheme.colorScheme.background,
        )
    }
}

@Composable
fun GreetingText() {
    Column {
        Text(
            "Sistem informasi Desa",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Selamat Malam",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 16.sp
        )
    }
}

@Composable
fun OldPlaceCard(place: Place, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Premium",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(place.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text("Dapatkan kemudahan atur keuangan")
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "More"
            )
        }
    }
}

@Composable
fun PlaceCard(
    place: Place,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onCardClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp) // Rounded corners for a modern look
    ) {
        Column {
            // Thumbnail Image
            Image(
                painter = rememberAsyncImagePainter(place.thumbnail),
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)), // Clip the image to match card corners
                contentScale = ContentScale.Crop // Crop the image if needed
            )
            // Title Text
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            // short desc
            place.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview()
@Composable()
fun HomeScreenPreview() {
    AppTheme(darkTheme = false) {
//        HomeScreen(navController = NavHostController(context = LocalContext.current))
    }
}
