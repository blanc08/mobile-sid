package com.blanc08.sid.feature.place

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
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
    viewModel: NewPlaceViewModel = hiltViewModel(),
) {
    val place by viewModel.place.collectAsState()
    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var byteArraySize by remember { mutableStateOf<Int?>(null) }
    val isSaved by viewModel.saved.collectAsState()


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

    LaunchedEffect(isSaved) {
        if (isSaved) {
            onBackClick()
        }
    }

    fun onSubmitButtonClick() {
        Log.d("NewPlaceScreen", "starting saving process")
        var byteArray: ByteArray? = null

        if (selectedUri != null) {
            byteArray = uriToByteArray(context, selectedUri!!)
        }

        byteArraySize = byteArray?.size
        if (byteArray == null) {
            Log.d("NewPlaceScreen", "photo is required")
            return
        }

        if (place.name == "") {
            Log.d("NewPlaceScreen", "name is required")
            return
        }

        if (place.description == "") {
            Log.d("NewPlaceScreen", "description is required")
            return
        }

        viewModel.save(byteArray)
        place.image?.let { Log.d("NewPlaceScreen", "Photo: $it") }
    }

    Column {
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

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(5.dp)
                .verticalScroll(state = rememberScrollState())


        ) {
            if (selectedUri != null) {
                AsyncImage(
                    model = selectedUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Fit
                )
            }
            // Image Picker
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Choose image"
                )
            }
            // Name
            TextField(
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    viewModel.updateUsername(it)
                },
                singleLine = true,
                value = place.name,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )

            // Description
            TextField(
                label = { Text("Deskripsi") },
                onValueChange = {
                    viewModel.updateDescription(it)
                },
                value = place.description,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                ),
                supportingText = {
                    Text(text = "minimum 100 karakter")
                },
                modifier = Modifier.fillMaxWidth(),
            )

            // Description
            TextField(
                label = { Text("Lokasi") },
                onValueChange = {
                    viewModel.updateLocation(it)
                },
                value = place.location ?: "",
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    Text(text = "tidak wajib, cantumkan nama landmark atau alamat lengkap yang ingin ditampikan di peta (dapat disalin dari google map)")
                }
            )


            Button(
                onClick = { onSubmitButtonClick() }
            ) {
                Text("Post")
            }
        }
    }
}
