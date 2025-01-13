package com.liveweather.weatherAPI

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/v1/current.json")
    suspend fun getData(
        @Query("key") apiKey: String,
        @Query("q") city: String,
    ):Response<WeatherModal>
}