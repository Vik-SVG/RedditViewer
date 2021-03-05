package com.vkpriesniakov.redditviewer

import android.app.Application
import com.vkpriesniakov.redditviewer.data.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                mainModule
            )
        }
    }
}