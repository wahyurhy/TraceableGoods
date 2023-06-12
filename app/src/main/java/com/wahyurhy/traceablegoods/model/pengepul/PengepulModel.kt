package com.wahyurhy.traceablegoods.model.pengepul


import com.google.gson.annotations.SerializedName

data class PengepulModel(
    @SerializedName("result")
    val result: List<Result>
)