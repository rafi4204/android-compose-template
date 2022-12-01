package com.composetemplate

import android.annotation.SuppressLint
import android.app.Application
import com.composetemplate.features.nstack.Translation
import dagger.hilt.android.HiltAndroidApp
import dk.nodes.nstack.kotlin.NStack
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    @SuppressLint("AppOpenMissing")
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}