package com.liveweather.di

sealed class  NetworkModule<out T>{
    data class Success<out T>(val data: T) : NetworkModule<T>()
    data class Error(val message: String) : NetworkModule<Nothing>()
    data object Loading : NetworkModule<Nothing>()

}