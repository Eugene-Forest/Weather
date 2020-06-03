package com.example.weather

import android.content.Context
import android.content.SharedPreferences
import android.inputmethodservice.InputMethodService
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
    val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    lateinit var sp:SharedPreferences

    //当前activity失去焦点时，将当前的城市名和坐标存储在SharedPreferences中
    override fun onPause() {
        super.onPause()

        sp.edit {
            putString("center",viewModel.center)
            putString("placeName",viewModel.placename)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        Log.i("weatherTest","----in WeatherActivity----")

        //获取SharedPreferences对象
        sp=SunnyWeatherApplication.context.getSharedPreferences("Place", Context.MODE_PRIVATE)

        //如果数据持有者的坐标数据为空字符串，通过意图获取
        if (viewModel.center.isEmpty()){
            //如果意图中没有对应的数据，则返回空字符串
            viewModel.center=intent.getStringExtra("center") ?: ""
        }
        if (viewModel.placename.isEmpty()){
            viewModel.placename=intent.getStringExtra("placeName") ?: ""
        }

        //对数据持有者的天气数据进行监视
        viewModel.weatherLiveData.observe(this, Observer {result->
            val weather=result.getOrNull()
            if (weather!=null){
                showWeatherInfo(weather)
            }
            else{
                Toast.makeText(this,"无法获取天气信息",Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.refreshWeather(viewModel.center)
        //刷新天气信息
        smartRefresh.setOnRefreshListener{
            viewModel.refreshWeather(viewModel.center)
            smartRefresh.finishRefresh()
        }


        search_btn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
                //关闭输入法
                val manager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerOpened(drawerView: View) {
            }

        })
    }


    fun showWeatherInfo(weather:Weather){

        val realtime=weather.realtime
        val daily=weather.daily

        //填充now布局的信息
        cityNameTextView.text=viewModel.placename
        tempTextView.text="${realtime.temperature.toInt()} ℃"
        weatherTextView.text= getSky(realtime.skycon).info

        //转换空气指数格式
        val aqi=realtime.airQuality.aqi.chn.toInt()
        airQualityTextView.text=getAriQuality(aqi)

        val windPower=realtime.wind.speed.toInt()
        val windDirection=realtime.wind.direction
        windTextView.text=getWindPower(windPower)
        WindDirectionTextView.text=getWindDirection(windDirection)

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

    private fun getAriQuality(aqi:Int):String{
        when {
            aqi in 0..50 -> {
                return "空气 优"
            }
            aqi in 51..100 -> {
                return "空气 良"
            }
            aqi in 101..150 -> {
                return "轻度污染"
            }
            aqi in 151..200 -> {
                return  "中度污染"
            }
            aqi>200 -> {
                return "重度污染"
            }
            else -> {
                return "空气指数 $aqi"
            }
        }
    }

    private fun getWindPower(windPower:Int):String{
        return when{
            windPower<1->{
                "无风"
            }
            windPower in 1..28->{
                "和风"
            }
            windPower in 29..61->{
                "强风"
            }
            windPower in 62..88->{
                "狂风"
            }
            windPower in 89..117->{
                "暴风"
            }
            windPower in 118..133->{
                "飓风"
            }
            windPower in 134..149->{
                "台风"
            }
            windPower in 150..183->{
                "强台风"
            }
            else->{
                "超强台风"
            }
        }
    }

    private fun getWindDirection(windDirection:Float):String{
        return when{
            windDirection in 22.6..67.5->{
                "东北风"
            }
            windDirection in 67.6..112.5->{
                "东风"
            }
            windDirection in 112.6..157.5->{
                "东南风"
            }
            windDirection in 157.6..202.5->{
                "南风"
            }
            windDirection in 202.6..247.5 ->{
                "西南风"
            }
            windDirection in 247.6..292.5->{
                "西风"
            }
            windDirection in 292.6..337.5->{
                "西北风"
            }
            else->{
                "北风"
            }
        }
    }
}
