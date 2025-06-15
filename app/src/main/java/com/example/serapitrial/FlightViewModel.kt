package com.example.serapitrial

import FlightResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import network.DispatcherProvider
import network.FlightApiService
import network.NetworkMonitor
import javax.inject.Inject

sealed class FlightsUIState {
    object Idle : FlightsUIState()
    object Loading : FlightsUIState()
    data class Success(val flights: List<FlightResponse>) : FlightsUIState()
    data class Error(val message: String) : FlightsUIState()
}


@HiltViewModel
class FlightViewModel @Inject constructor(
    private val apiService: FlightApiService,
    private val networkMonitor: NetworkMonitor,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _flightsUIState = MutableStateFlow<FlightsUIState>(FlightsUIState.Idle)
    val flightsUIState: StateFlow<FlightsUIState> = _flightsUIState


    private var isConnected = false

    init {
        Log.d("FlightViewModel", "Initializing ViewModel")
        checkInitialNetworkStatus()
        observeNetworkStatus()
    }

    private fun checkInitialNetworkStatus() {
        isConnected = networkMonitor.isNetworkAvailable()
        Log.d("FlightViewModel", "Initial network status: $isConnected")

        _flightsUIState.value = if (isConnected) {
            FlightsUIState.Loading
        } else {
            Log.e("FlightViewModel", "Network unavailable at launch")
            FlightsUIState.Error("No internet connection")
        }
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkMonitor.observeNetworkStatus().collectLatest { status ->
                isConnected = status
                Log.d("FlightViewModel", "Network status changed: $isConnected")
                _flightsUIState.value = if (isConnected) {
                    FlightsUIState.Loading
                } else {
                    Log.e("FlightViewModel", "Lost network connection")
                    FlightsUIState.Error("No internet connection")
                }
            }
        }
    }

    fun getFlights(
        departureId: String,
        arrivalId: String,
        outboundDate: String,
        returnDate: String,
        type: Int = 1
    ) {
        Log.d("FlightViewModel", "Fetching flights: $departureId ➡ $arrivalId on $outboundDate")
        viewModelScope.launch(dispatcherProvider.IO) {
            _flightsUIState.value = FlightsUIState.Loading
            try {
                val response = apiService.searchFlights(
                    departureId = departureId,
                    arrivalId = arrivalId,
                    outboundDate = outboundDate,
                    returnDate = null,
                    type = type
                )
                val allFlights = (response.bestFlights ?: emptyList()) + (response.otherFlights ?: emptyList())
                _flightsUIState.value = FlightsUIState.Success(allFlights)

                Log.d(
                    "FlightViewModel",
                    "API call success: ${allFlights.size} results"
                )
                Log.d("FlightViewModel", "Flights UI state updated to Success")

            } catch (e: Exception) {
                Log.e("FlightViewModel", "API call failed", e)
                _flightsUIState.value =
                    FlightsUIState.Error("Something went wrong: ${e.localizedMessage}")
            }
        }
    }
}
