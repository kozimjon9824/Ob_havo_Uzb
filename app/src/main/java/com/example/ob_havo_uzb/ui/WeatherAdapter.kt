package com.example.ob_havo_uzb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ob_havo_uzb.R
import com.example.ob_havo_uzb.databinding.RecyclerItemBinding
import com.example.ob_havo_uzb.model.Daily

class WeatherAdapter (private val data:ArrayList<Daily>):RecyclerView.Adapter<WeatherAdapter.MainViewHolder>(){


    inner class MainViewHolder(private val binding: RecyclerItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(currentData:Daily){

            val  date = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
            format(java.util.Date(currentData.dt *1000 ));

            binding.nextDate.text=date.subSequence(0,2)
            binding.nextTemp.text=(currentData.temp.day.toInt()).toString()+"°"

            val mainPic=binding.nextPic

            val picUrl= data[0].weather[0].icon


            when {
                (picUrl == "01d") or (picUrl == "01n") -> {
                    mainPic.setImageResource(R.drawable.sun)
                }
                (picUrl == "02d") or (picUrl == "02n") -> {
                    mainPic.setImageResource(R.drawable.cloudy)
                }
                (picUrl == "03d") or (picUrl == "03n") or (picUrl == "04d") or (picUrl == "04n") or (picUrl == "11n") or (picUrl == "11d") -> {
                    mainPic.setImageResource(R.drawable.cloud_computing)
                }
                (picUrl == "09d") or (picUrl == "09n") or (picUrl == "10d") or (picUrl == "10n") -> {
                    mainPic.setImageResource(R.drawable.rainy)
                }
                (picUrl == "13d") or (picUrl == "13n") -> {
                    mainPic.setImageResource(R.drawable.snowing)
                }
                (picUrl == "50d") or (picUrl == "50n") -> {
                    mainPic.setImageResource(R.drawable.haze)
                }
            }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding=RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainViewHolder(binding)
    }

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =holder.bind(data[position])

}