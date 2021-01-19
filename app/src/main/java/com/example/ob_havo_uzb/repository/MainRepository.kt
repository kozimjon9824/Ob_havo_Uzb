package com.example.ob_havo_uzb.repository

import com.example.ob_havo_uzb.retrofit.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService){

     suspend fun getData(lat:Float,lon:Float)=apiService.getData(lat,lon)

}