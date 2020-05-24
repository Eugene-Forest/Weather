package com.example.weather.logic.network

import android.util.Log
import com.example.weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object WeatherNetwork {
    private val placeService=ServiceCreator.create(PlaceService::class.java)

    private val weatherService=ServiceCreator.create2(WeatherService::class.java)

    suspend fun searchPlace(query:String)= placeService.searchPlace(query).await()

    suspend fun getRealtimeWeather(center:String)
            = weatherService.searchRealtimeWeatherInfo(center).await()
    suspend fun getDailyWeather(center: String)
            = weatherService.searchDailyWeatherInfo(center).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {continuation ->
            enqueue(
                object : Callback<T>{
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        Log.i("weatherTest","get failure")
                        continuation.resumeWithException(t)
                    }

                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        val body=response.body()
                        if (body!=null){
                            Log.i("weatherTest","send require and get")
                            continuation.resume(body)
                        }
                        else{
                            continuation.resumeWithException(RuntimeException("" +
                                    "response body is null"))
                        }
                    }

                }
            )
        }
    }
}