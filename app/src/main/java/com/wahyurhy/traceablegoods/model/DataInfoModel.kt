package com.wahyurhy.traceablegoods.model


import com.google.gson.annotations.SerializedName

data class DataInfoModel(
    @SerializedName("result")
    val result: List<Result>
)