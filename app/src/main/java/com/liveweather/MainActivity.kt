package com.liveweather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import com.liveweather.modals.WeatherViewModal
import com.liveweather.ui.theme.LiveWeatherTheme
import com.liveweather.ui.theme.WeatherScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Set status bar to dark icons
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars=false

        var _viewModal = ViewModelProvider(this)[WeatherViewModal::class.java]
        setContent {
            LiveWeatherTheme {
                Surface(

                    color = colorResource(id=com.liveweather.R.color.white)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        // Add the background image
                        Image(
                            painter = painterResource(id = com.liveweather.R.drawable.bg), // Replace with your image resource
                            contentDescription = null, // Decorative image, no description needed
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillWidth // Scale the image to cover the entire background
                        )

                        // Overlay the WeatherScreen on top of the image
                        WeatherScreen(_viewModal)
                    }
                }
            }
        }
    }
}

