package com.example.weather.logic.model

import com.google.gson.annotations.SerializedName

//实时天气模型数据
/*
数据模型的类的参数名对应API获取数据的内部类的对象名，
并根据从API获取的数据类的内部类结构创建相同的类结构（只需要创建对应所需数据的类结构模型即可）
 */
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

