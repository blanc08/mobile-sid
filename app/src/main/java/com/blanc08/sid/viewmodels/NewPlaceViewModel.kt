package com.blanc08.sid.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.blanc08.sid.data.place.Place
import com.blanc08.sid.data.place.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class NewPlaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PlaceRepository,
) : ViewModel() {

    private val _place = MutableStateFlow(Place(name = "Default name", id = "9999"))
    val place: StateFlow<Place> = _place.asStateFlow()

    companion object {
        private const val TAG = "NewPlaceViewModel"
        private const val PLACE_ID_SAVED_STATE_KEY = "id"
    }


}