package com.example.weather.logic.model



//定义网络请求的数据的数据模型

data class PlaceResponse(val status:String,val districts:List<Place>){
}

data class Place(val name:String,val center:String)