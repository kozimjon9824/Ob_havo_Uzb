package com.example.ob_havo_uzb.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class MyPreferences(context: Context) {

    companion object {
        private const val DARK_STATUS = "Status"
    }

    private val preferences =context.getSharedPreferences("mode",Context.MODE_PRIVATE)

    var darkMode = preferences.getInt(DARK_STATUS, 0)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()

}