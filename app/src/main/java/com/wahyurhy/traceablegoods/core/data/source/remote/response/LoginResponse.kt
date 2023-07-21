package com.wahyurhy.traceablegoods.core.data.source.remote.response

import com.wahyurhy.traceablegoods.core.data.source.model.Admin

data class LoginResponse(
    val code: Int,
    val message: String,
    val data: Admin
)