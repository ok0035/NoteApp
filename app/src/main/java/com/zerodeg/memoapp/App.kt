package com.zerodeg.memoapp

import android.app.Application
import android.content.Context
import android.util.Log

class App: Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: App

        fun applicationContext():Context {
            return instance.applicationContext
        }

        fun log(name:String, content:String) {
            Log.d(name, content)
        }
    }

}