package com.wahyurhy.traceablegoods.model.alurdistribusi


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("batch_id")
    val batchId: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("distributor")
    val distributor: String,
    @SerializedName("jenis_produk")
    val jenisProduk: String,
    @SerializedName("kategori_panerima")
    val kategoriPanerima: String,
    @SerializedName("lokasi_asal")
    val lokasiAsal: String,
    @SerializedName("lokasi_tujuan")
    val lokasiTujuan: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("produk")
    val produk: String,
    @SerializedName("produk_batch")
    val produkBatch: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("tahap")
    val tahap: String,
    @SerializedName("total_yang_didistribusikan")
    val totalYangDidistribusikan: String,
    @SerializedName("total_yang_diterima")
    val totalYangDiterima: String
) : Parcelable