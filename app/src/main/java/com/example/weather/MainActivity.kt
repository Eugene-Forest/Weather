package com.example.weather

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather.logic.model.PlaceResponse
import com.example.weather.logic.network.PlaceService
import com.example.weather.logic.network.ServiceCreator
import com.example.weather.ui.place.PlaceViewModel
import com.example.weather.ui.weather.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获取SharedPreferences对象
        sp=SunnyWeatherApplication.context.getSharedPreferences("Place", Context.MODE_PRIVATE)

        val center=sp.getString("center","")
        val placeName=sp.getString("placeName","")
        Log.i("weatherTest",center)
        Log.i("weatherTest",placeName)

        //如果SharedPreferences中有数据，则直接转到天气显示界面，否则，显示搜索界面
        if (center!=""&&placeName!=""){
            val intent=Intent(this,WeatherActivity::class.java).apply {
                putExtra("center",center)
                putExtra("placeName",placeName)
            }
            startActivity(intent)
            finish()
        }
        
        //加载搜索界面
        setContentView(R.layout.activity_main)
    }
}
