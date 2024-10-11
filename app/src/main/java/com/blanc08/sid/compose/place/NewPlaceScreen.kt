package com.blanc08.sid.compose.place

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.blanc08.sid.viewmodels.NewPlaceViewModel

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use { it.readBytes() } // Read bytes from the input stream
    } catch (e: Exception) {
        Log.e("PhotoPicker", "Error converting URI to ByteArray", e)
        null
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaceScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier, // padding rendered too much with this component composition
    viewModel: NewPlaceViewModel = hiltViewModel(),
) {
    val place by viewModel.place.collectAsState()
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var byteArraySize by remember { mutableStateOf<Int?>(null) }


    // Registers a photo picker activity launcher in single-select mode.
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                selectedUri = uri
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        // modifier = modifier
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("New Place")
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Home"
                    )
                }
            },
        )
        Column(modifier = Modifier.padding(5.dp)) {
            // Image Picker
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text(
                    text = "Choose image"
                )
            }
            // Name
            TextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.updateUsername(it)
                },
                value = place.name
            )
            // Description
            TextField(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.updateDescription(it)
                },
                value = place.description
            )
            Button(
                onClick = {
                    var byteArray: ByteArray? = null

                    if (selectedUri != null) {
                        byteArray = uriToByteArray(context, selectedUri!!)
                    }

                    byteArraySize = byteArray?.size

                    if (byteArray != null) {
                        viewModel.uploadPhoto(byteArray)
                        place.image?.let { Log.d("NewPlaceScreen", "Photo: $it") }
                    }
                }
            ) {
                Text("Post")
            }
        }
    }
}
