package com.sergeiiashin.notebook

import android.app.Application
import com.github.ajalt.timberkt.Timber
import com.sergeiiashin.notebook.di.appModule
import com.sergeiiashin.notebook.di.mainModule
import com.sergeiiashin.notebook.di.noteModule
import com.sergeiiashin.notebook.di.splashModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber.log.Timber.DebugTree())
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}