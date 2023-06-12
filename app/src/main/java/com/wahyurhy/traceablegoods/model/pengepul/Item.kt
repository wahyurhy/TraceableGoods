package com.wahyurhy.traceablegoods.model.pengepul


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("alamat_pengepul")
    val alamatPengepul: String,
    @SerializedName("kontak_pengepul")
    val kontakPengepul: String,
    @SerializedName("nama_pengepul")
    val namaPengepul: String,
    @SerializedName("pengepul_id")
    val pengepulId: Int
)