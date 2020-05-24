package com.example.weather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
获取PlaceService接口的动态管理对象
作为连接应用程序网络请求接口与面向数据源的网络请求接口的连接者
 */

object ServiceCreator {
    private const val BASE_URL="https://restapi.amap.com/"
    private const val BASE_URL2="https://api.caiyunapp.com/"
    //定义并获取一个retrofit对象，为获取PlaceService接口的动态管理对象而准备
    private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit2=Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /*
    定义一个可供外部使用的函数，使得其可以通过该方法得到PlaceService接口的实例对象，
    然后通过实例使用接口里的方法
     */
    /*
    retrofit.create(serviceClass)返回一个serviceClass :Class<T>的实例，故而
    create(...)函数返回值类型为T；
    使用该方法等同于使用retrofit.create(serviceClass)
     */
    fun <T> create(serviceClass :Class<T>):T= retrofit.create(serviceClass)

    //通过泛型实化来获取PlaceService接口的动态代理对象
    inline fun <reified T> create():T= create(T::class.java)


    fun <T> create2(weatherService:Class<T>):T= retrofit2.create(weatherService)
}