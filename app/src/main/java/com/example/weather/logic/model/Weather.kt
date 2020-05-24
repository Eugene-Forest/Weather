package com.example.weather.logic.model

//封装
data class Weather(val realtime:RealtimeWeatherInfo.Realtime,
                   val daily:DailyWeatherInfo.Daily)