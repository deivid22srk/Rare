package com.rare.launcher

import android.app.Application

class RareApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    companion object {
        lateinit var instance: RareApplication
            private set
    }
}
