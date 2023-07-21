package com.wahyurhy.traceablegoods.core.data.source.remote

import com.wahyurhy.traceablegoods.core.data.source.remote.network.ApiService

class RemoteDataSource(private val api: ApiService) {

    suspend fun login(email: String, password: String) = api.login(email, password)

}