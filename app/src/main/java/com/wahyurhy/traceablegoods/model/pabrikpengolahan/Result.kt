package com.wahyurhy.traceablegoods.model.pabrikpengolahan


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("title")
    val title: String
)