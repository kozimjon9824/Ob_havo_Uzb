package com.uzbsoft.ob_havo_uzb.retrofit

import androidx.lifecycle.LiveData
import com.uzbsoft.ob_havo_uzb.model.ResponseWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("onecall?lang=en&units=metric&exclude=hourly,minutely&appid=14edf6914b8d32c7cbdc65565d873b71")
    suspend fun getData(@Query("lat") lat:Float, @Query("lon") lon:Float):Response<ResponseWeather>

}