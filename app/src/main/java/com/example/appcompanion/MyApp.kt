package com.example.appcompanion

import android.app.Application
import com.example.appcompanion.utilities.firebase.FB

class MyApp: Application() {

    companion object {
        private lateinit var instance: MyApp
        fun get() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        FB.init(this)
        FB.analytics.logOpenApp()
    }
}