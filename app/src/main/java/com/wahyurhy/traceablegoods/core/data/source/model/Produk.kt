package com.wahyurhy.traceablegoods.core.data.source.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produk(
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("jenis_produk")
    val jenisProduk: String,
    @SerializedName("merek")
    val merek: String,
    @SerializedName("nama_produk")
    val namaProduk: String,
    @SerializedName("nomor_lot")
    val nomorLot: String,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("tanggal_kadaluarsa")
    val tanggalKadaluarsa: String,
    @SerializedName("tanggal_produksi")
    val tanggalProduksi: String,
    @SerializedName("timestamp")
    val timeStamp: String
): Parcelable