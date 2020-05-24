package com.example.weather.logic.network

import com.example.weather.SunnyWeatherApplication
import com.example.weather.logic.model.DailyWeatherInfo
import com.example.weather.logic.model.RealtimeWeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.Token}/{center}/realtime.json")
    fun searchRealtimeWeatherInfo(@Path("center") center:String): Call<RealtimeWeatherInfo>

    @GET("v2.5/${SunnyWeatherApplication.Token}/{center}/daily.json")
    fun searchDailyWeatherInfo(@Path("center") center:String): Call<DailyWeatherInfo>

}