package com.shishir.weatherapp.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishir.weatherapp.data.repository.WeatherRepository
import com.shishir.weatherapp.utils.DEFAULT_WEATHER_DESTINATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import com.shishir.weatherapp.utils.Result
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<DashboardUiState> =
        MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _searchBarState: MutableState<SearchBarState> =
        mutableStateOf(value = SearchBarState.CLOSED)
    val searchBarState: State<SearchBarState> = _searchBarState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchBarState(newValue: SearchBarState) {
        _searchBarState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    init {
        getWeather()
    }

    fun getWeather(city: String = DEFAULT_WEATHER_DESTINATION) {
        repository.getWeatherForecast(city.trim()).map { result ->
            when (result) {
                is Result.Success -> {
                    _uiState.value = DashboardUiState(weatherModel = result.data)
                }

                is Result.Error -> {
                    _uiState.value = DashboardUiState(errorMessage = result.errorMessage)
                }

                Result.Loading -> {
                    _uiState.value = DashboardUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}