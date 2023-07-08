package com.wahyurhy.traceablegoods.model.produk


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: ArrayList<Item>,
    @SerializedName("title")
    val title: String
)