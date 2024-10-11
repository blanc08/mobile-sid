package com.blanc08.sid.viewmodels

import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanc08.sid.data.photo.Photo
import com.blanc08.sid.data.photo.PhotoRepository
import com.blanc08.sid.feature.gallery.navigation.PHOTO_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PhotoRepository,
) : ViewModel() {
    private val photoId: StateFlow<String?> = savedStateHandle.getStateFlow(PHOTO_ID_ARG, null)

    private val _photo = MutableStateFlow<Photo?>(null)
    val photo: StateFlow<Photo?> = _photo.asStateFlow()

    init {
        refreshData()
    }


    private fun refreshData() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "get photo params: ${photoId.value}")
                val photoIdValue = photoId.value

                if (photoIdValue.isNullOrEmpty()) {
                    throw NotFoundException("Not Found")
                }

                val result = repository.getPhoto(photoIdValue)
                Log.d(TAG, "get photo result: " + _photo.value.toString())

                _photo.value = result
                Log.d(TAG, "Photo value: " + _photo.value.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "PhotoViewModel"
    }


}