package com.blanc08.sid.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanc08.sid.data.place.FieldValidation
import com.blanc08.sid.data.place.NewPlaceDao
import com.blanc08.sid.data.place.NewPlaceDaoValidation
import com.blanc08.sid.data.place.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPlaceViewModel @Inject constructor(
    private val repository: PlaceRepository,
) : ViewModel() {

    // data state
    private val _place = MutableStateFlow(NewPlaceDao())
    val place: StateFlow<NewPlaceDao> = _place.asStateFlow()

    // validation state
    private val _validation = MutableStateFlow(NewPlaceDaoValidation())
    val validation: StateFlow<NewPlaceDaoValidation> = _validation.asStateFlow()


    private val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    fun updateUsername(input: String) {
        _place.value = _place.value.copy(name = input)
    }

    fun updateDescription(input: String) {
        _place.value = _place.value.copy(description = input)
    }

    fun updateLocation(input: String) {
        _place.value = _place.value.copy(location = input)
    }

    fun save(byteArray: ByteArray) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "saving new place to database...")

                // validation
                if (_place.value.name.isEmpty()) {
                    _validation.value = _validation.value.copy(
                        nameValidator = FieldValidation(
                            isValid = false,
                            message = "invalid name"
                        )
                    )
                    return@launch
                }

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