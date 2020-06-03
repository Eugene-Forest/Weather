package com.example.weather.logic.model



//定义网络请求的数据的数据模型
/*
数据模型的类的参数名对应API获取数据的内部类的对象名，
并根据从API获取的数据类的内部类结构创建相同的类结构（只需要创建对应所需数据的类结构模型即可）
 */
data class PlaceResponse(val status:String,val districts:List<Place>){
}

data class Place(val name:String,val center:String)