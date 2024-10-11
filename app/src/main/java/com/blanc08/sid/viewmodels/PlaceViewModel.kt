package com.blanc08.sid.viewmodels

import android.content.res.Resources.NotFoundException
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanc08.sid.data.place.Place
import com.blanc08.sid.data.place.PlaceRepository
import com.blanc08.sid.feature.place.navigation.PLACE_ID_ARG
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
    private val placeId: StateFlow<String?> = savedStateHandle.getStateFlow(PLACE_ID_ARG, null)

    private val _place = MutableStateFlow<Place?>(null)
    val place: StateFlow<Place?> = _place.asStateFlow()

    init {
        refreshData()
    }


    private fun refreshData() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "get place params: ${placeId.value}")
                val placeIdValue = placeId.value

                if (placeIdValue.isNullOrEmpty()) {
                    throw NotFoundException("Not Found")
                }

                val result = repository.getPlace(placeIdValue)
                Log.d(TAG, "get place result: " + _place.value.toString())

                _place.value = result
                Log.d(TAG, "Place value: " + _place.value.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val TAG = "PlaceViewModel"
    }


}