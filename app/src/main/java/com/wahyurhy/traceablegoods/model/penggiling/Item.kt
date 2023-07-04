package com.wahyurhy.traceablegoods.model.penggiling


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("alamat_penggiling")
    val alamatPenggiling: String,
    @SerializedName("kontak_penggiling")
    val kontakPenggiling: String,
    @SerializedName("nama_penggiling")
    val namaPenggiling: String,
    @SerializedName("penggiling_id")
    val penggilingId: Int,
    @SerializedName("timestamp")
    val timestamp: String
)