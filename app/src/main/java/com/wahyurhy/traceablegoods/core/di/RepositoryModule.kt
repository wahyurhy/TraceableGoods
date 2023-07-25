package com.wahyurhy.traceablegoods.core.di

import com.wahyurhy.traceablegoods.core.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get()) }
}