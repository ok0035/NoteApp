package com.zerodeg.memoapp

import android.app.Application
import android.content.Context

class App: Application() {



    init {
        instance = this
    }

    companion object {
        lateinit var instance: App

        fun applicationContext():Context {
            return instance.applicationContext
        }
    }

}