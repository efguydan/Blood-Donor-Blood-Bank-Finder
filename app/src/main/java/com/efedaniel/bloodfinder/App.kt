package com.efedaniel.bloodfinder

import android.app.Application
import android.os.Build
import androidx.work.*
import com.efedaniel.bloodfinder.di.AppComponent
import com.efedaniel.bloodfinder.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class App : Application() {

    private var applicationScope = CoroutineScope(Dispatchers.Default)
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .application(this)
                .build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
