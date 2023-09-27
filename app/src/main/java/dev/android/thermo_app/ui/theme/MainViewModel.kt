package dev.android.thermo_app.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    // State variables for Celsius and Fahrenheit temperatures
    private val _celsiusTemperature = MutableStateFlow(0.0)
    private val _fahrenheitTemperature = MutableStateFlow(32.0)

    // LiveData or State properties for exposing the temperatures
    val celsiusTemperature: StateFlow<Double>  = _celsiusTemperature.asStateFlow()
    val fahrenheitTemperature: StateFlow<Double> = _fahrenheitTemperature.asStateFlow()

    // Function to update Celsius temperature and calculate Fahrenheit
    fun setCelsiusTemperature(celsius: Double) {
        _celsiusTemperature.value = celsius
        _fahrenheitTemperature.value = celsiusToFahrenheit(celsius)
    }

    // Function to update Fahrenheit temperature and calculate Celsius
    fun setFahrenheitTemperature(fahrenheit: Double) {
        _fahrenheitTemperature.value = fahrenheit
        _celsiusTemperature.value = fahrenheitToCelsius(fahrenheit)
    }

    // Conversion functions
    private fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    private fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
}