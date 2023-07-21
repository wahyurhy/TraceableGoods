package com.wahyurhy.traceablegoods.core.data.source.remote.network

import com.wahyurhy.traceablegoods.core.data.source.remote.response.LoginResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("register")
    suspend fun register(): Response<RequestBody>

}