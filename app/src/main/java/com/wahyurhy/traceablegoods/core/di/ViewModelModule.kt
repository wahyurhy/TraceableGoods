package com.wahyurhy.traceablegoods.core.di

import com.wahyurhy.traceablegoods.ui.activity.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}