package dev.android.thermo_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.android.thermo_app.ui.theme.Thermo_AppTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Thermo_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var temperature by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedConversion by remember { mutableStateOf(TemperatureConversion.FahrenheitToCelsius) }
    val convertedTemperature = if (temperature.isNotEmpty()) {
        val inputTemperature = temperature.toDouble()
        when (selectedConversion) {
            TemperatureConversion.FahrenheitToCelsius -> convertFahrenheitToCelsius(inputTemperature)
            TemperatureConversion.CelsiusToFahrenheit -> convertCelsiusToFahrenheit(inputTemperature)
        }
    } else {
        0.0
    }


    val context = LocalContext.current
    var result by remember { mutableStateOf("") }
    val temp = arrayOf("FahrenheitToCelsius", "CelsiusToFahrenheit")

    var selectedText by remember { mutableStateOf(temp[0]) }

    Column() {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    temp.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item // Update the displayed text
                                expanded = false // Close the dropdown
                                // Update the selectedConversion based on the selected item
                                selectedConversion = when (item) {
                                    "FahrenheitToCelsius" -> TemperatureConversion.FahrenheitToCelsius
                                    "CelsiusToFahrenheit" -> TemperatureConversion.CelsiusToFahrenheit
                                    else -> selectedConversion // Handle other cases if needed
                                }
                            }
                        )
                    }

                    /*  coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            if (selectedText=="")
                        }
                    )
                } */
                }
            }
        }

        TextField(
            value = temperature,
            onValueChange = {
                temperature = it // Update the user-entered value
                //  updateResult()
            },
            label = { Text("Enter Temperature") }, // Optional label for the TextField
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )




        TextField(
            value = "${convertedTemperature.roundToInt()}",
            onValueChange = { /* No-op, this is a read-only TextField */ },
            label = { Text("Result") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}
    enum class TemperatureConversion {
        FahrenheitToCelsius,
        CelsiusToFahrenheit
    }

    fun convertFahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }

    fun convertCelsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }



@Preview(showBackground = true)
@Composable
fun GreetingPreview1() {
    Thermo_AppTheme {
       App()
    }
}