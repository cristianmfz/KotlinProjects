package com.android.firebasesongs

import android.app.Application
import android.content.Context

class FirebaseSongsApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}