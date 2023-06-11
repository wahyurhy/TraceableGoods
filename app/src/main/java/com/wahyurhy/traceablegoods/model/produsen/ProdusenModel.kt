package com.wahyurhy.traceablegoods.model.produsen


import com.google.gson.annotations.SerializedName

data class ProdusenModel(
    @SerializedName("result")
    val result: List<Result>
)