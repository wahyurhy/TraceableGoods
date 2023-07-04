package com.wahyurhy.traceablegoods.model.datainfo


import com.google.gson.annotations.SerializedName

data class DataInfoModel(
    @SerializedName("result")
    var result: List<Result>
)