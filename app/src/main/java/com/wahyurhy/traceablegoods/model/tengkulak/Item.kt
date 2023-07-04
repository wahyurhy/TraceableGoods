package com.wahyurhy.traceablegoods.model.tengkulak


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("alamat_tengkulak")
    val alamatTengkulak: String,
    @SerializedName("kontak_tengkulak")
    val kontakTengkulak: String,
    @SerializedName("nama_tengkulak")
    val namaTengkulak: String,
    @SerializedName("tengkulak_id")
    val tengkulakId: Int,
    @SerializedName("timestamp")
    val timestamp: String
)