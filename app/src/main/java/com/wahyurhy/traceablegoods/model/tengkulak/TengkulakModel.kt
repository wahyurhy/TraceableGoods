package com.wahyurhy.traceablegoods.model.tengkulak


import com.google.gson.annotations.SerializedName

data class TengkulakModel(
    @SerializedName("result")
    val result: List<Result>
)