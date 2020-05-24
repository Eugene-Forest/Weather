package com.example.weather.ui.place

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment:Fragment() {
    //获取viewModel实例,为的是能从数据持有者中获取数据
    private val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //对recyclerview设置布局为线性布局
        val layoutManager=LinearLayoutManager(activity)
        recyclerview.layoutManager=layoutManager
        //绑定recyclerview的适配器
        adapter= PlaceAdapter(this,viewModel.places)
        recyclerview.adapter=adapter

        searchPlaceEdit.addTextChangedListener { text->
            val content=text.toString()
            if (content.isNotEmpty()){
                viewModel.getName(content)
            }
            else{
                recyclerview.visibility=View.GONE
                bgImageView.visibility=View.VISIBLE

                viewModel.places.clear()
                adapter.notifyDataSetChanged()
            }

        }

        //对placeListData进行监视
        viewModel.placeListData.observe(viewLifecycleOwner, Observer {result->
            val places=result.getOrNull()
            if(places!=null){
                recyclerview.visibility=View.VISIBLE
                bgImageView.visibility=View.GONE
                //把viewModel.placeList原有的元素清除
                viewModel.places.clear()
                //把places中的元素全部添加到placeList中
                viewModel.places.addAll(places)
                adapter.notifyDataSetChanged()
            }
            else{
                Toast.makeText(activity,"未能查询到任何地点", Toast.LENGTH_SHORT).show()
            }
        })
    }
}