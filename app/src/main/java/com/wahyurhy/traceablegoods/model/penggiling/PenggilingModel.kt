package com.wahyurhy.traceablegoods.model.penggiling


import com.google.gson.annotations.SerializedName

data class PenggilingModel(
    @SerializedName("result")
    val result: List<Result>
)