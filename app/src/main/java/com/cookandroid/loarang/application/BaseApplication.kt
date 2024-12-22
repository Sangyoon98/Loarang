package com.cookandroid.loarang.application

import android.app.Application
import com.cookandroid.loarang.SharedPreferenceUtil

class BaseApplication: Application() {
    companion object {
        var instance: BaseApplication? = null

        // SharedPreference
        lateinit var sharedPreferenceUtil: SharedPreferenceUtil
        lateinit var PREFERENCE_NAME: String
    }

    override fun onCreate() {
        super.onCreate()
        PREFERENCE_NAME = this.packageName
        sharedPreferenceUtil = SharedPreferenceUtil(applicationContext, PREFERENCE_NAME)
        instance = this
    }
}