package com.example.weather.ui.place

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.WeatherActivity
import com.example.weather.logic.model.Place

//传入fragment对象（显示的上下文）和place数组（显示的数据源）
class PlaceAdapter(private val fragment: Fragment,private val placeList:List<Place>)
    :RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val placeName:TextView=view.findViewById(R.id.placeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //绑定适配器
        val view=LayoutInflater.from(parent.context).inflate(R.layout.place_item,
            parent,false)

        //为每个搜索后显示的卡片添加加点击事件
        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener {
            //获取当前的下标
            val position=holder.adapterPosition
            //根据下标从地址数组中找出该地址的信息
            val place=placeList[position]
            //创建意图，当发生点击事件是，跳转到天气信息显示界面
            val intent=Intent(parent.context,WeatherActivity::class.java).apply {
                putExtra("center",place.center)
                putExtra("placeName",place.name)
                Log.i("weatherTest","${place.center}")
                Log.i("weatherTest","${place.name}")
            }
            Log.i("weatherTest","----open WeatherActivity----")
            fragment.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //对每一个子布局的数据进行赋值
        val place=placeList[position]
        holder.placeName.text=place.name
    }

    override fun getItemCount(): Int {
       return placeList.size
    }

}