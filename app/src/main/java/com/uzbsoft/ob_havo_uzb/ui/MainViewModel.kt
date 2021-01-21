package com.uzbsoft.ob_havo_uzb.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzbsoft.ob_havo_uzb.model.ResponseWeather
import com.uzbsoft.ob_havo_uzb.repository.MainRepository
import com.uzbsoft.ob_havo_uzb.util.Resource
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(private val repository: MainRepository):ViewModel(){


    private val data:MutableLiveData<Resource<ResponseWeather>> = MutableLiveData()

    fun getData(lat:Float,lon:Float): MutableLiveData<Resource<ResponseWeather>> {
        loadData(lat,lon)
        return data
    }

    private fun loadData(lat:Float,lon:Float){
        viewModelScope.launch {
            val m=repository.getData(lat, lon)
            data.value= Resource.loading()
            if (m.isSuccessful){
                data.value= Resource.success(m.body() as ResponseWeather)
            }
            else{
                data.value= Resource.error(m.message()+"aaa")
            }
        }
    }

}