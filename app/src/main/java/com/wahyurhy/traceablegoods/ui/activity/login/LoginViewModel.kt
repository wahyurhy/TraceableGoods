package com.wahyurhy.traceablegoods.ui.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wahyurhy.traceablegoods.core.data.repository.AppRepository

class LoginViewModel(val repo: AppRepository) : ViewModel() {

    fun login(email: String, password: String) = repo.login(email, password).asLiveData()

}