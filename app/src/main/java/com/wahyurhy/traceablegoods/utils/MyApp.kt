package com.wahyurhy.traceablegoods.utils

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.wahyurhy.traceablegoods.core.di.appModule
import com.wahyurhy.traceablegoods.core.di.repositoryModule
import com.wahyurhy.traceablegoods.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}