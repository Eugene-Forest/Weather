package com.example.weather.logic.model

//封装实时天气和预报天气信息
data class Weather(val realtime:RealtimeWeatherInfo.Realtime,
                   val daily:DailyWeatherInfo.Daily)