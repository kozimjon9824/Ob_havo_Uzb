package com.example.ob_havo_uzb

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.example.ob_havo_uzb.databinding.ActivityBase1Binding
import com.example.ob_havo_uzb.model.GeoCodesModel
import com.example.ob_havo_uzb.ui.WeatherFragment
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var lat = 41.26465F
    private var lon = 69.21627F

    private lateinit var data:ArrayList<GeoCodesModel>
    private var cityId=0
    private var cityName="Tashkent"

    private lateinit var pref:SharedPreferences

    private lateinit var binding: ActivityBase1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBase1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        pref=getSharedPreferences("WEATHER", Context.MODE_PRIVATE)

        lat=pref.getFloat("LAT",lat)
        lon=pref.getFloat("LAT",lon)
        cityName= pref.getString("CITY",cityName).toString()

        val bundle = bundleOf("LAT" to lat, "LON" to lon, "NAME" to cityName)

        if (this.isOnline()){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.container_fragment,WeatherFragment::class.java,bundle,"TAG")
            }
        }else{
            Toast.makeText(this,"Internetni yoqing!",Toast.LENGTH_SHORT).show()
        }

        binding.searchLocation.setOnClickListener {
            fullData()
            openDialog()
        }

    }

    private fun openDialog() {
        val listItems = data.map { it.name }

        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an city")


        var checkedItem = cityId
        builder.setSingleChoiceItems(listItems.toTypedArray(), checkedItem) { dialog, which  ->
            checkedItem=which
            cityId=which

            lat=data[cityId].lat.toFloat()
            lon=data[cityId].lon.toFloat()
            cityName=data[cityId].name
        }


        builder.setPositiveButton("OK") { dialog, which ->
            // user clicked OK
            val edit=pref.edit()
            edit.clear()
            edit.putString("CITY",cityName)
            edit.putFloat("LAT",data[cityId].lat.toFloat())
            edit.putFloat("LON",data[cityId].lon.toFloat())
            edit.apply()

            EventBus.getDefault().post(GeoCodesModel(cityName,data[cityId].lat,data[cityId].lon) )
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel"){dialog, which -> dialog.dismiss()}

        val dialog = builder.create()
        dialog.show()
    }

    private fun fullData(){
        data=ArrayList()
        data.add(GeoCodesModel(	"Tashkent", 41.26465, 69.21627))
        data.add(GeoCodesModel("Namangan",40.9983, 71.67257))
        data.add(GeoCodesModel(	"Samarkand",	39.65417, 66.95972))
        data.add(GeoCodesModel(	"Bukhara",39.77472, 64.42861))
        data.add(GeoCodesModel(	"Nukus",42.45306, 59.61028))
        data.add(GeoCodesModel(	"Qarshi",38.86056, 65.78905))
        data.add(GeoCodesModel(	"Kokand",40.52861, 70.9425))
        data.add(GeoCodesModel("Fergana",40.38421, 71.78432))
        data.add(GeoCodesModel(	"Andijon",40.78206, 72.34424))
        data.add(GeoCodesModel("Jizzax",40.12341, 67.82842))
        data.add(GeoCodesModel(	"Navoiy",40.08444, 65.37917))
        data.add(GeoCodesModel(	"Asaka",40.64153, 72.23868))
        data.add(GeoCodesModel(	"Chirchiq",41.46889, 69.58222))
        data.add(GeoCodesModel(	"Urganch",	41.55339, 60.62057))
        data.add(GeoCodesModel(	"Tirmiz",37.22417, 67.27833))
        data.add(GeoCodesModel("Angren",41.01667, 70.14361))
        data.add(GeoCodesModel(	"Olmaliq",40.84472, 69.59833))
        data.add(GeoCodesModel(	"Bekobod",40.22083, 69.26972))
    }
    fun Context.isOnline(): Boolean {
        val netInfo =
            (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
