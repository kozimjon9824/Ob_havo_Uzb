package com.uzbsoft.ob_havo_uzb.model

import com.google.gson.annotations.SerializedName

data class ResponseWeather(
    @SerializedName("lat")
    val lat:Float,
    @SerializedName("lon")
    val lon:Float,
    @SerializedName("timezone")
    val timezone:String,
    @SerializedName("timezone_offset")
    val timezoneOff:Int,
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily:ArrayList<Daily>
    )


data class Daily(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dew_point: Float,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    val weather: ArrayList<Weather>,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("pop")
    val pop: Float,
    @SerializedName("uvi")
    val uvi: Float
)

data class Current(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dew_point: Float,
    @SerializedName("uvi")
    val uvi: Float,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    val weather: ArrayList<Weather>
    )

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Temp(
@SerializedName("day")
val day:Float,
@SerializedName("min")
val min:Float,
@SerializedName("max")
val max:Float,
@SerializedName("night")
val night:Float,
@SerializedName("eve")
val eve:Float,
@SerializedName("morn")
val morn:Float
)

data class FeelsLike(
    @SerializedName("day")
    val day:Float,
    @SerializedName("night")
    val night:Float,
    @SerializedName("eve")
    val eve:Float,
    @SerializedName("morn")
    val morn:Float)

/*

data class Hourly(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("feels_like")
    val feelsLike: Float,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dew_point: Float,
    @SerializedName("uvi")
    val uvi: Int,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Float,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("weather")
    val weather: ArrayList<Weather>,
    @SerializedName("pop")
    val pop:Int
)
*/


