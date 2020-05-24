package com.example.weather.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.weather.logic.model.*
import com.example.weather.logic.network.PlaceService
import com.example.weather.logic.network.ServiceCreator
import com.example.weather.logic.network.WeatherNetwork
import com.example.weather.logic.network.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

//对数据进行获取的仓库
object Repository {

    fun getPlaces(query:String)= liveData{
        val result=try {
            val placeResponse=WeatherNetwork.searchPlace(query)
            if (placeResponse.status=="1"){
                val places=placeResponse.districts
                Result.success(places)
            }
            else{
                Result.failure(
                    RuntimeException("" +
                            "response status is ${placeResponse.status}")
                )
            }
        }
        catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result as Result<List<Place>>)
    }


    fun getWeather(center:String)= liveData {
        val result=try {
            //对实时天气与未来天气同时的获取用并发实现
            coroutineScope {
                val deferredRealtime=async {
                    WeatherNetwork.getRealtimeWeather(center)
                }
                val deferredDaily=async {
                    WeatherNetwork.getDailyWeather(center)
                }
                val realtimeResponse=deferredRealtime.await()
                val dailyResponse=deferredDaily.await()

                if (realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                    val weather=Weather(realtimeResponse.result.realtime
                        ,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure<Weather>(
                        RuntimeException(
                            "RealtimeResponse status is ${realtimeResponse.status} "+ "\n"+
                                    "DailyResponse status is ${dailyResponse.status}"))
                }
            }
        }
        catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }
}