package com.example.weather

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
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("weatherTest","mainActivity")
    }
}
