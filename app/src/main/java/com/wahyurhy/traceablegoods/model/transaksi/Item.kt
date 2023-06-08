package com.wahyurhy.traceablegoods.model.transaksi


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("batch_id")
    val batchId: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("jenis_produk")
    val jenisProduk: String,
    @SerializedName("penerima")
    val penerima: String,
    @SerializedName("produk")
    val produk: String,
    @SerializedName("produk_batch")
    val produkBatch: String
)