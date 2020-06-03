package com.example.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

//获取全局context的类
class SunnyWeatherApplication :Application(){

    //定义伴生对象
    companion object{

        //定义令牌值
        const val Token="66rZnW0UWjKmTUcG"
        const val key="439f3b01751abda9fc2cc53dfb38eb8b"

        //定义一个全局的上下文context变量，应用于整个应用程序存活的全部阶段
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        //获取context
        context=applicationContext
    }
}