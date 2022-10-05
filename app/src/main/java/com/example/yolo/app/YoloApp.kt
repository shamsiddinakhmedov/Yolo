package com.example.yolo.app

import android.app.Application
import timber.log.Timber

class YoloApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (com.example.yolo.BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}