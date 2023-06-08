package com.wahyurhy.traceablegoods.model.datainfo


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("data_count")
    val dataCount: String,
    @SerializedName("data_name")
    val dataName: String,
    @SerializedName("id")
    val id: Int
)