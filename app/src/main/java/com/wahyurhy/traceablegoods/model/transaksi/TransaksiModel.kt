package com.wahyurhy.traceablegoods.model.transaksi


import com.google.gson.annotations.SerializedName

data class TransaksiModel(
    @SerializedName("result")
    val result: List<Result>
)