package com.cookandroid.loarang

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context, preferenceName: String) {
    private val preference: SharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

    fun setBooleanPreference(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    fun getBooleanPreference(key: String): Boolean {
        return preference.getBoolean(key, false)
    }
}