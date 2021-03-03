package com.vkpriesniakov.redditviewer

import android.app.Application
import com.vkpriesniakov.redditviewer.data.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidLogger


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            // use modules
            modules(
                retrofitModule
            )
        }
    }
}