package com.example.weather.logic.model

import com.google.gson.annotations.SerializedName

//实时天气模型数据

data class RealtimeWeatherInfo(val status: String,val result: RealtimeResult){
    data class RealtimeResult(val realtime:Realtime){}

    data class Realtime(val temperature:Float
                        ,val skycon:String
                        ,val wind:Wind
                        ,@SerializedName("air_quality") val airQuality:AriQuality ){}

    data class AriQuality(val aqi:AOI){}

    data class AOI(val chn:Float){}

    data class Wind(val speed:Float,val direction:Float){}
}

