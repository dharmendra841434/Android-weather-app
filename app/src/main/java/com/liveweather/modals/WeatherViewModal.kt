package com.liveweather.modals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liveweather.di.NetworkModule
import com.liveweather.weatherAPI.RetrofitInstance
import com.liveweather.weatherAPI.WeatherModal
import kotlinx.coroutines.launch

class WeatherViewModal : ViewModel() {

   private val weatherAPI =RetrofitInstance.weatherAPI

    private val _weatherData = MutableLiveData<NetworkModule<WeatherModal>>()
    val weatherData: LiveData<NetworkModule<WeatherModal>> = _weatherData

     fun getWeatherData (city: String){

         _weatherData.value = NetworkModule.Loading
         viewModelScope.launch {
            try {
                val response  =  weatherAPI.getData("83aaebf34a64412a9f9104133250301",city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _weatherData.value = NetworkModule.Success(it)
                    }
                    Log.i("getWeatherData: ",response.body().toString())
                }else{
                    _weatherData.value = NetworkModule.Error("Data not found")
                    Log.i("WeatrherDataError: ",response.message().toString())
                }
            }catch (exception: Exception){
                _weatherData.value = NetworkModule.Error("Failed to fetch weather data")
                Log.i("WeatherDataException: ",exception.message.toString())

            }
         }
    }
}