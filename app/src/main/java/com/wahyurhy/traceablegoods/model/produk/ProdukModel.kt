package com.wahyurhy.traceablegoods.model.produk


import com.google.gson.annotations.SerializedName

data class ProdukModel(
    @SerializedName("result")
    val result: List<Result>
)