package com.wahyurhy.traceablegoods.model.distributor


import com.google.gson.annotations.SerializedName

data class DistributorModel(
    @SerializedName("result")
    val result: List<Result>
)