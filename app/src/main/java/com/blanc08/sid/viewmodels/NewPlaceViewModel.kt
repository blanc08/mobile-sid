package com.blanc08.sid.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanc08.sid.data.place.NewPlace
import com.blanc08.sid.data.place.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPlaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PlaceRepository,
) : ViewModel() {

    private val _place = MutableStateFlow(NewPlace())
    val place: StateFlow<NewPlace> = _place.asStateFlow()
    private val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    fun updateUsername(input: String) {
        _place.value = _place.value.copy(name = input)
    }

    fun updateDescription(input: String) {
        _place.value = _place.value.copy(description = input)
    }

    fun save(byteArray: ByteArray) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "saving new place to database...")

                val path = repository.uploadFile(byteArray)
                _place.value = _place.value.copy(image = path, thumbnail = path)

                val response = repository.createOne(_place.value)
                Log.d(TAG, "new place saved${response.id}")

                if (response.id != null) {
                    _saved.value = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "NewPlaceViewModel"
        private const val PLACE_ID_SAVED_STATE_KEY = "id"
    }


}