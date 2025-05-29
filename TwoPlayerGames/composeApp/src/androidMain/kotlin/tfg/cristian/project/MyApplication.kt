package tfg.cristian.project

import android.app.Application
import org.koin.android.ext.koin.androidContext
import tfg.cristian.project.di.initializeKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(this@MyApplication)
        }
    }
}