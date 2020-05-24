package com.example.weather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

//天气预报数据模型

data class DailyWeatherInfo(val status:String,val result:Result){

    data class Result(val daily:Daily)

    data class Daily(val temperature:List<Temperature>
                     ,val skycon:List<Skycon>
                     ,@SerializedName("life_index")val lifeIndex:LifeIndex)


    data class Temperature(val max:Float,val min:Float)

    data class Skycon(val date: Date,val value:String)

    data class LifeIndex(val ultraviolet:List<LifeDescription>
                         ,val dressing:List<LifeDescription>
                         ,val carWashing:List<LifeDescription>
                         ,val coldRisk:List<LifeDescription>)

    data class LifeDescription(val desc:String)

}