package com.example.transcore.apllication.mainApp

import android.app.Application
import com.example.transcore.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TranslatorApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.d("TransCore Application started")
    }

}