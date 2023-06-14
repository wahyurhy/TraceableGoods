package com.wahyurhy.traceablegoods.model.alurdistribusi


import com.google.gson.annotations.SerializedName

data class AlurDistribusiModel(
    @SerializedName("result")
    var result: List<Result>
)