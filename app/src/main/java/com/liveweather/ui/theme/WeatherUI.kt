package com.liveweather.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.liveweather.weatherAPI.WeatherModal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


data class WeatherDetails(
    val temperature: String,
    val realFeel: String,
    val humidity: String,
    val pressure: String,
    val wind: String,
    val uvIndex: String,
    val waterTemperature: String,
    val date: String,
    val currentLocation : String,
    val icon:String,
    val condition:String

)

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(inputDateTime: String): String {
    // Parse the input string into a LocalDateTime object
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    val dateTime = LocalDateTime.parse(inputDateTime, inputFormatter)

    // Format the LocalDateTime into the desired output string
    val outputFormatter = DateTimeFormatter.ofPattern("EEEE — dd MMM", Locale.ENGLISH)
    return dateTime.format(outputFormatter)
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(data:WeatherModal) {

    val weather = WeatherDetails(
        temperature = "${data.current.temp_c}°",
        realFeel="${data.current.feelslike_c}°",
        humidity = "${data.current.humidity} ",
        pressure = "${data.current.pressure_mb} mmHg",
        wind = "${data.current.wind_kph} m/s",
        uvIndex = data.current.uv,
        waterTemperature = data.current.cloud,
        date = "${formatDate(data.current.last_updated)}",
        currentLocation="${data.location.name}, ${data.location.country}",
        icon="${data.current.condition.icon}",
        condition = data?.current?.condition?.text.toString()

    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            WeatherHeader(weather)
            /*Spacer(modifier = Modifier.height(24.dp))*/
            WeatherDetailsGrid(weather)

        }
    }
}




@Composable
fun WeatherHeader(weather: WeatherDetails) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = weather.date,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.LightGray,

        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = weather.temperature,
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text ="Weather of ${weather.currentLocation}",
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            color = Color.White
        )

        AsyncImage(
            model = "https:${weather.icon}".replace("64x64", "128x128"), contentDescription = "", modifier = Modifier.size(140.dp)
        )
        Text(text = weather.condition, color = Color.LightGray, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
fun WeatherDetailsGrid(weather: WeatherDetails) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailItem("Real Feel", weather.realFeel)
            WeatherDetailItem("Humidity", weather.humidity)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailItem("Pressure", weather.pressure)
            WeatherDetailItem("Wind", weather.wind)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            WeatherDetailItem("UV Index", weather.uvIndex)
            WeatherDetailItem("Water", weather.waterTemperature)
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}




