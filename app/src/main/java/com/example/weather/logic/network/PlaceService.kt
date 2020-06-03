package com.example.weather.logic.network

import com.example.weather.SunnyWeatherApplication
import com.example.weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//面向数据源的网络请求接口
//实现对数据获取的面向数据源的网络请求接口
interface PlaceService {

    @GET("v3/config/district?key=${SunnyWeatherApplication.key}" +
            "&subdistrict=0&extensions=base&offset=10")
    fun searchPlace(@Query("keywords") query: String): Call<PlaceResponse>

}