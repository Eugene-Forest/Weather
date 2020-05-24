package com.example.weather

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.logic.model.Weather
import com.example.weather.logic.model.getSky
import com.example.weather.ui.weather.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    //获取天气的数据持有者
    private val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        Log.i("weatherTest","----in WeatherActivity----")

        //如果数据持有者的坐标数据为空字符串，通过意图获取
        if (viewModel.center.isEmpty()){
            //如果意图中没有对应的数据，则返回空字符串
            viewModel.center=intent.getStringExtra("center") ?: ""
//            Log.i("weatherTest","weather--place--center")
//            Log.i("weatherTest",viewModel.center)
        }
        if (viewModel.placename.isEmpty()){
            viewModel.placename=intent.getStringExtra("placeName") ?: ""
//            Log.i("weatherTest","weather--place--name")
//            Log.i("weatherTest",viewModel.placename)
        }

        //对数据持有者的天气数据进行监视
        viewModel.weatherLiveData.observe(this, Observer {result->
            val weather=result.getOrNull()
            if (weather!=null){
//                Log.i("weatherTest","weather--daily")
//                Log.i("weatherTest","${weather.daily.toString()}")
//                Log.i("weatherTest","weather--realtime")
//                Log.i("weatherTest","${weather.realtime.toString()}")
                showWeatherInfo(weather)
            }
            else{
                Toast.makeText(this,"无法获取天气信息",Toast.LENGTH_SHORT).show()
            }
        })

        //刷新天气信息
        viewModel.refreshWeather(viewModel.center)
    }

    fun showWeatherInfo(weather:Weather){

        val realtime=weather.realtime
        val daily=weather.daily

        //填充now布局的信息
        cityNameTextView.text=viewModel.placename
        tempTextView.text="${realtime.temperature.toInt()} ℃"
        weatherTextView.text= getSky(realtime.skycon).info
        airQualityTextView.text="空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)

        //填充forecast布局信息
        //移除原来的控件
        forecastLayout.removeAllViews()
        //填充以下控件

        //获取预报的天数
        val days=daily.skycon.size
        for (i in 0 until days){
            //获取这天的天气信息
            val skycon=daily.skycon[i]
            val temperature=daily.temperature[i]

            //向创建预报子布局，添加配置并获取其控件
            val view=layoutInflater.inflate(R.layout.forecast_item,
                forecastLayout,false)
            val dateInfo=view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon=view.findViewById<ImageView>(R.id.skyIcon)
            val skyInfo=view.findViewById<TextView>(R.id.skyInfo)
            val tempInfo=view.findViewById<TextView>(R.id.tempInfo)

            //向子布局的控件添加信息
            //获取日期的转换格式
            val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
            //根据转换格式转化日期
            dateInfo.text=simpleDateFormat.format(skycon.date)
            val sky= getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val temp="${temperature.min} ~ ${temperature.max} ℃"
            tempInfo.text=temp

            //将天气预报子布局添加到天气预报父布局中
            forecastLayout.addView(view)
        }

        //填充当天的生活指数布局数据
        val lifeIndex=daily.lifeIndex
        coldRiskText.text=lifeIndex.coldRisk[0].desc
        dressingText.text=lifeIndex.dressing[0].desc
        ultravioletText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carWashing[0].desc
        weatherLayout.visibility=View.VISIBLE
    }
}
