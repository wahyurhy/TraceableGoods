package com.wahyurhy.traceablegoods.core.data.source.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaksi(
    @SerializedName("transaksi_id")
    var transaksiId: Int,
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
    @SerializedName("harga_jual")
    var hargaJual: String,
    @SerializedName("status")
    var status: String
): Parcelable