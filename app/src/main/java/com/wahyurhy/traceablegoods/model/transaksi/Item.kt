package com.wahyurhy.traceablegoods.model.transaksi


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("batch_id")
    var batchId: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("jenis_produk")
    var jenisProduk: String,
    @SerializedName("penerima")
    var penerima: String,
    @SerializedName("produk")
    var produk: String,
    @SerializedName("produk_batch")
    var produkBatch: String,
    @SerializedName("status")
    var status: String
)