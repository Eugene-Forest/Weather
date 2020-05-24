package com.example.weather.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.weather.logic.Repository
import com.example.weather.logic.model.Place


class PlaceViewModel:ViewModel() {

    val nameLiveData=MutableLiveData<String>()

    //添加对搜索名的监视，当改变时，调用并返回值LiveData<List<Place>>
    //使用Transformations.switchMap来监视该对象是因为每次返回的对象都是一个新对象
    val placeListData =Transformations.switchMap(nameLiveData){query->
        Repository.getPlaces(query)
    }

    fun getName(query:String){
        nameLiveData.value=query
    }

    val places=ArrayList<Place>()

}