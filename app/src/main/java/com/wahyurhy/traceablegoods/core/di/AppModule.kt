package com.wahyurhy.traceablegoods.core.di

import com.wahyurhy.traceablegoods.core.data.source.remote.RemoteDataSource
import com.wahyurhy.traceablegoods.core.data.source.remote.network.ApiConfig
import org.koin.dsl.module

val appModule = module {
    single { ApiConfig.provideApiService }
    single { RemoteDataSource(get()) }
}