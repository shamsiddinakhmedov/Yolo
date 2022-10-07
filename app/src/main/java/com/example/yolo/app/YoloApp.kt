package com.example.yolo.app

import android.app.Application
import timber.log.Timber
import com.example.yolo.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YoloApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}