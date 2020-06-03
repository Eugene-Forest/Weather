package com.example.weather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

//天气预报数据模型
/*
数据模型的类的参数名对应API获取数据的内部类的对象名，
并根据从API获取的数据类的内部类结构创建相同的类结构（只需要创建对应所需数据的类结构模型即可）
 */
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