package com.blanc08.sid.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanc08.sid.data.place.Place
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

    private val _place = MutableStateFlow(Place())
    val place: StateFlow<Place> = _place.asStateFlow()

    fun updateUsername(input: String) {
        _place.value = _place.value.copy(name = input)
    }

    fun updateDescription(input: String) {
        _place.value = _place.value.copy(description = input)
    }

    fun uploadPhoto(byteArray: ByteArray) {
        // upload to supabase storage
        viewModelScope.launch {
            val path = repository.uploadFile(byteArray)
            _place.value = _place.value.copy(image = path)
        }
        // update image field
    }

    companion object {
        private const val TAG = "NewPlaceViewModel"
        private const val PLACE_ID_SAVED_STATE_KEY = "id"
    }


}