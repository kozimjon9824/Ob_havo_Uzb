package com.example.ob_havo_uzb.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.ob_havo_uzb.R
import com.example.ob_havo_uzb.databinding.FragmentWeatherBinding
import com.example.ob_havo_uzb.model.GeoCodesModel
import com.example.ob_havo_uzb.model.ResponseWeather
import com.example.ob_havo_uzb.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class WeatherFragment :Fragment(R.layout.fragment_weather){

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel:MainViewModel by viewModels()

    private var lat = 41.26465F
    private var lon = 69.21627F
    private var cityName="Tashkent"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentWeatherBinding.inflate(inflater,container,false)
        return binding.root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(geoCodesModel: GeoCodesModel){
        lat = geoCodesModel.lat.toFloat()
        lon=geoCodesModel.lon.toFloat()
        cityName=geoCodesModel.name

        getDataFromServer()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lat= requireArguments().getFloat("LAT")
        lon=requireArguments().getFloat("LON")
        cityName=requireArguments().get("NAME").toString()

        if (requireContext().isOnline()){
            getDataFromServer()
        }else{
            Toast.makeText(context,"Internetni yoqing!",Toast.LENGTH_SHORT).show()
        }

    }

    private fun getDataFromServer(){
        showOrHideProgress(true)

        viewModel.getData(lat = lat,lon = lon).observe(viewLifecycleOwner){
            if (it.status== Resource.Status.SUCCESS){
                val mainData=it.data
                mainData?.let { it1 -> loadDataToViews( it1) }
                showOrHideProgress(false)
            }
            else if(it.status==Resource.Status.ERROR){
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                showOrHideProgress(false)
            }
        }
    }


    private fun Context.isOnline(): Boolean {
        val netInfo =
            (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun showOrHideProgress(isOn:Boolean){
        if (isOn){
            binding.progressBar.visibility=View.VISIBLE
        }
        else{
            binding.progressBar.visibility=View.INVISIBLE
        }
    }


    private fun loadDataToViews(data:ResponseWeather){
        val dailyInfo=data.daily


        val adapter= dailyInfo?.let { it1 -> WeatherAdapter(it1) }
        binding.recyclerView.adapter=adapter

        //city name         (41.311081, 69.240562)
        binding.cityName.text= cityName
        binding.mainDate.text=convertDateToUnderstandable(dailyInfo[0].dt).subSequence(9,19)
        binding.mainTemp.text= (dailyInfo?.get(0)?.temp?.day?.toInt()).toString() + "°"
        binding.mainHumidity.text=dailyInfo?.get(0)?.humidity.toString()+"%"
        binding.windSpeed.text=dailyInfo?.get(0)?.windSpeed.toString()+" m/s"
        binding.mainDescription.text=dailyInfo?.get(0)?.weather?.get(0)?.description
        binding.mainTempMax.text="max:  "+(dailyInfo?.get(0)?.temp?.max?.toInt()) +"°"
        binding.mainTempMin.text="min:  "+(dailyInfo?.get(0)?.temp?.min?.toInt()) +"°"
        binding.mainTempCurrent.text="curr:  "+(data.current.temp.toInt())+"°"
        binding.mainSunRise.text=convertDateToUnderstandable(dailyInfo[0].sunrise).subSequence(0,5)
        binding.mainSunSet.text=convertDateToUnderstandable(dailyInfo[0].sunset).subSequence(0,5)

        val picUrl= dailyInfo[0].weather[0].icon

        when {
            (picUrl == "01d") or (picUrl == "01n") -> {
                binding.mainPic.setImageResource(R.drawable.sun)
            }
            (picUrl == "02d") or (picUrl == "02n") -> {
                binding.mainPic.setImageResource(R.drawable.cloudy)
            }
            (picUrl == "03d") or (picUrl == "03n") or (picUrl == "04d") or (picUrl == "04n") or (picUrl == "11n") or (picUrl == "11d") -> {
                binding.mainPic.setImageResource(R.drawable.cloud_computing)
            }
            (picUrl == "09d") or (picUrl == "09n") or (picUrl == "10d") or (picUrl == "10n") -> {
                binding.mainPic.setImageResource(R.drawable.rainy)
            }
            (picUrl == "13d") or (picUrl == "13n") -> {
                binding.mainPic.setImageResource(R.drawable.snowing)
            }
            (picUrl == "50d") or (picUrl == "50n") -> {
                binding.mainPic.setImageResource(R.drawable.haze)
            }
        }
    }
    private fun convertDateToUnderstandable(number:Long): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy").
        format(Date(number * 1000 ));
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}
