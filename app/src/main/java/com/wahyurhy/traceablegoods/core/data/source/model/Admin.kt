package com.wahyurhy.traceablegoods.core.data.source.model

import com.google.gson.annotations.SerializedName

data class Admin(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
