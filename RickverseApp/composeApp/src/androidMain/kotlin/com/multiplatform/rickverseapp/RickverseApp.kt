package com.multiplatform.rickverseapp

import android.app.Application
import com.multiplatform.rickverseapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class RickverseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@RickverseApp)
        }
    }
}