package com.wahyurhy.traceablegoods.model.alurdistribusi


import com.google.gson.annotations.SerializedName

data class Detail(
    @SerializedName("date")
    var date: String,
    @SerializedName("distributor")
    var distributor: String,
    @SerializedName("jenis_produk")
    var jenisProduk: String,
    @SerializedName("kategori_panerima")
    var kategoriPanerima: String,
    @SerializedName("lokasi_asal")
    var lokasiAsal: String,
    @SerializedName("lokasi_tujuan")
    var lokasiTujuan: String,
    @SerializedName("nama")
    var nama: String,
    @SerializedName("produk")
    var produk: String,
    @SerializedName("produk_batch")
    var produkBatch: String,
    @SerializedName("total_yang_didistribusikan")
    var totalYangDidistribusikan: String,
    @SerializedName("total_yang_diterima")
    var totalYangDiterima: String
)