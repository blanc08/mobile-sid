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
import com.blanc08.sid.data.photo.PhotoRepository
import com.blanc08.sid.data.place.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject internal constructor(
    private val repository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _pictures = MutableStateFlow(emptyList<Photo>())
    val pictures: StateFlow<List<Photo>> = _pictures.asStateFlow()

    fun loadAll(offset: String = "") {
        viewModelScope.launch {
            val result = repository.getPlaces(offset)

            Log.d(TAG, "pictures fetched: ${result.size}")
            _pictures.value = result
        }

    }

    init {
        Log.d("PlaceListViewModel", "Loading Place List View Model")
        loadAll()
    }


    companion object {
        private const val TAG = "GalleryViewModel"
    }
}