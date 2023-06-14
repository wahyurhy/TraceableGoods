package com.wahyurhy.traceablegoods.model.transaksi


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    var id: Int,
    @SerializedName("items")
    var items: List<Item>,
    @SerializedName("title")
    var title: String
)