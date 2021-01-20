package com.example.ob_havo_uzb

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.example.ob_havo_uzb.databinding.ActivityBase1Binding
import com.example.ob_havo_uzb.model.GeoCodesModel
import com.example.ob_havo_uzb.ui.WeatherFragment
import com.example.ob_havo_uzb.util.GpsTracker
import com.example.ob_havo_uzb.util.MyPreferences
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var lat = 41.26465F
    private var lon = 69.21627F
    private val TAG="AAA"
    private lateinit var data:ArrayList<GeoCodesModel>
    private var cityId=0
    private var cityName="Tashkent"
    private lateinit var pref:SharedPreferences
    private lateinit var binding: ActivityBase1Binding
    private lateinit var gps:GpsTracker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBase1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocation()

        if (this.isOnline()){
            startFragment()
         }else{
            Toast.makeText(this,"Internetni yoqing!",Toast.LENGTH_SHORT).show()
        }

        loadData()
        binding.searchLocation.setOnClickListener {
            openDialog()
        }

        /////////////////////
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }



    private fun startFragment(){
        val bundle = bundleOf("LAT" to lat, "LON" to lon, "NAME" to cityName)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.container_fragment,WeatherFragment::class.java,bundle,"TAG")
        }

    }

    private fun getLocation() {
        gps = GpsTracker(this@MainActivity)
        if (gps.canGetLocation()) {
            lat  = gps.getLatitude().toFloat()
             lon = gps.getLongitude().toFloat()
            val geocoder=Geocoder(this, Locale.getDefault())
            val address:ArrayList<Address>
            address= geocoder.getFromLocation(lat.toDouble(),lon.toDouble(),1) as ArrayList<Address>
            cityName=address[0].locality
        } else {
            gps.showSettingsAlert()
        }
    }


    private fun openDialog() {
        val listItems = data.map { it.name }

        // setup the alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Shahar tanlang!")


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
            EventBus.getDefault().post(GeoCodesModel(cityName,data[cityId].lat,data[cityId].lon) )
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel"){dialog, which -> dialog.dismiss()}
        val dialog = builder.create()
        dialog.show()
    }

    private fun openDialogForDayNightMode(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("mode tanlang")
        val checkedItem = MyPreferences(this).darkMode
        builder.setSingleChoiceItems(arrayOf("Light","Night"),checkedItem){dialog, which ->
            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()

                    dialog.dismiss()
                }
        }}

        builder.setPositiveButton("OK"){
            dialog, which ->

            dialog.dismiss()
        }
        val dialog=builder.create()
        dialog.show()
    }


    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
        }
    }


    private fun loadData(){
        data=ArrayList()
        data.add(GeoCodesModel(	"Tashkent", 41.26465, 69.21627))
        data.add(GeoCodesModel(	"Tashkent vil", 41.166666 ,69.749997))
        data.add(GeoCodesModel("Namangan",40.9983, 71.67257))
        data.add(GeoCodesModel(	"Andijon",40.78206, 72.34424))
        data.add(GeoCodesModel("Fergana",40.38421, 71.78432))
        data.add(GeoCodesModel(	"Qashqadaryo",38.83333 ,66.083333))
        data.add(GeoCodesModel(	"Surxondaryo",38.00000, 67.499998))
        data.add(GeoCodesModel(	"Samarkand",	39.65417, 66.95972))
        data.add(GeoCodesModel(	"Bukhara",39.77472, 64.42861))
        data.add(GeoCodesModel(	"Navoiy",40.08444, 65.37917))
        data.add(GeoCodesModel(	"Sirdaryo",40.416665,68.666664))
        data.add(GeoCodesModel("Jizzax",40.12341, 67.82842))
        data.add(GeoCodesModel(	"Xorazm",	41.333332 , 61.00000))
        data.add(GeoCodesModel(	"Qoraqalpogiston",43.166666, 58.749997))
        data.add(GeoCodesModel(	"Qarshi",38.86056, 65.78905))
        data.add(GeoCodesModel(	"Kokand",40.52861, 70.9425))
        data.add(GeoCodesModel(	"Asaka",40.64153, 72.23868))
        data.add(GeoCodesModel(	"Chirchiq",41.46889, 69.58222))
        data.add(GeoCodesModel(	"Nukus",42.45306, 59.61028))
        data.add(GeoCodesModel(	"Tirmiz",37.22417, 67.27833))
        data.add(GeoCodesModel("Angren",41.01667, 70.14361))
        data.add(GeoCodesModel(	"Olmaliq",40.84472, 69.59833))
        data.add(GeoCodesModel(	"Urganch",	41.55339, 60.62057))
        data.add(GeoCodesModel(	"Bekobod",40.22083, 69.26972))
    }

    private fun Context.isOnline(): Boolean {
        val netInfo =
            (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
