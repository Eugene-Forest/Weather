package com.example.weather.ui.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.weather.logic.Repository

class WeatherViewModel :ViewModel(){

    //定义一个储存当前显示的城市信息，为刷新天气信息而准备
    var center=""
    var placename=""

    //为center字符添加监视做准备，定义一个针对center的LiveData
    val centerLiveData=MutableLiveData<String>()

    //当centerLiveData的数据发生变化时从仓库中获取weather数据
    val weatherLiveData=Transformations.switchMap(centerLiveData){center->
        Repository.getWeather(center)
    }

    //通过该函数改变centerLiveData的数据，从而触发仓库的网络请求
    fun refreshWeather(center:String){
        Log.i("weatherTest","refresh center")
        centerLiveData.value=center
    }

}