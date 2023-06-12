package com.wahyurhy.traceablegoods.model.gudang


import com.google.gson.annotations.SerializedName

data class GudangModel(
    @SerializedName("result")
    val result: List<Result>
)