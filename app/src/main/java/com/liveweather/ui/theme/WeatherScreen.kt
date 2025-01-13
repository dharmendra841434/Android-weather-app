package com.liveweather.ui.theme


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.liveweather.di.NetworkModule
import com.liveweather.modals.WeatherViewModal


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen (viewModal: WeatherViewModal){

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult  =  viewModal.weatherData.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp,58.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                },
                modifier = Modifier.border(
                    width = 0.5.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(18.dp),
                ).width(250.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(text = "Enter City Name", color = Color.LightGray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp, // Set your desired font size here
                    fontFamily = FontFamily.Monospace,
                    color = Color.White
                )

            )
           Button(
               onClick = {
                   viewModal.getWeatherData(city) ;
                   keyboardController?.hide()
               },
               modifier = Modifier.height(60.dp),
               shape = RoundedCornerShape(15.dp)
           ) {
               Text(text = "Search", fontFamily = FontFamily.Monospace)
           }
        }

        Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center)
        {
            Column {
                when(val res = weatherResult.value){

                    is NetworkModule.Error -> {
                        Text("Message: ${res.message}")
                    }
                    NetworkModule.Loading -> {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                    is NetworkModule.Success ->{
                        WeatherCard(res.data)
                    }
                    null -> {
                       /* AsyncImage(
                            model = "https://cdn.weatherapi.com/weather/64x64/day/113.png",
                            contentDescription = "Weather Icon",
                            modifier = Modifier
                                .size(64.dp)
                                .padding(8.dp)
                        )*/
                    }
                }

            }
        }

    }
}