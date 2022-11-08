package com.sosa.final_project.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sosa.final_project.network.WeatherApi
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    /**
     * Call getWeather() on init so we can display status immediately.
     */
    init {
        getCurrentWeather()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [LiveData].
     */
    private fun getCurrentWeather {
        viewModelScope.launch {
            try {
                val listResult = WeatherApi.retrofitService.getWeather()
                _status.value = listResult
            }
            catch(e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
        _status.value = "Loading weather information..."
    }
}