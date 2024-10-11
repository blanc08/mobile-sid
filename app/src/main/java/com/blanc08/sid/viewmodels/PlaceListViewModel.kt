/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * The ViewModel for plant list.
 */
@HiltViewModel
class PlaceListViewModel @Inject internal constructor(
    private val repository: PlaceRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    fun loadPlaces(offset: String = "") {
        viewModelScope.launch {
            val result = repository.getPlaces(offset)  // Call suspend function inside a coroutine
            Log.d("PlaceListViewModel", "places fetched${result.toList()}")
            _places.value = result

            // TODO: push with delta item if offset provided
            // _places.value = _places.value.plus(result)
        }

    }

    init {
        Log.d("PlaceListViewModel", "Loading Place List View Model")
        loadPlaces()
    }


    companion object {
        private const val NO_GROW_ZONE = -1
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }
}