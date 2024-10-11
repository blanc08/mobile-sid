package com.blanc08.sid.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanc08.sid.data.photo.NewPhoto
import com.blanc08.sid.data.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPhotoViewModel @Inject constructor(
    private val repository: PhotoRepository,
) : ViewModel() {

    private val _photo = MutableStateFlow(NewPhoto())
    val photo: StateFlow<NewPhoto> = _photo.asStateFlow()
    private val _saved = MutableStateFlow(false)
    val isSaved = _saved.asStateFlow()

    fun updateDescription(input: String) {
        _photo.value = _photo.value.copy(description = input)
    }

    fun save(byteArray: ByteArray) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "saving new photo to database...")

                val path = repository.uploadFile(byteArray)
                _photo.value = _photo.value.copy(url = path)

                val response = repository.createOne(_photo.value)
                Log.d(TAG, "new photo saved: ${response.id}")

                _saved.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "NewPhotoViewModel"
    }


}