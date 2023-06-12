package com.wahyurhy.traceablegoods.model.penerima


import com.google.gson.annotations.SerializedName

data class PenerimaModel(
    @SerializedName("result")
    val result: List<Result>
)