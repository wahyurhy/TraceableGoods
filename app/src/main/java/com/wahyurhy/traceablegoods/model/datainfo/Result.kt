package com.wahyurhy.traceablegoods.model.datainfo


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    var id: Int,
    @SerializedName("items")
    var items: ArrayList<Item>,
    @SerializedName("title")
    var title: String
)