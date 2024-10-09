package com.blanc08.sid.viewmodels

import android.util.Log
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
class PlaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PlaceRepository,
) : ViewModel() {
    private val placeId: String = savedStateHandle.get<String>(PLACE_ID_SAVED_STATE_KEY)!!

    private val _place = MutableStateFlow<Place?>(null)
    val place: StateFlow<Place?> = _place.asStateFlow()

    init {
        refreshData()
    }


    private fun refreshData() {
        viewModelScope.launch {
            try {
                Log.d("PlaceViewModel", "get place params: $placeId")
                val result = repository.getPlace(placeId)
                Log.d("PlaceViewModel", "get place result: " + _place.value.toString())

                _place.value = result
                Log.d("PlaceViewModel", "Place value: " + _place.value.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val PLACE_ID_SAVED_STATE_KEY = "id"
    }


}