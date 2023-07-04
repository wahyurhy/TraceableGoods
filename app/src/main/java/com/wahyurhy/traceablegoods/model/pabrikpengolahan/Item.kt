package com.wahyurhy.traceablegoods.model.pabrikpengolahan


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("alamat_pabrik_pengolahan")
    val alamatPabrikPengolahan: String,
    @SerializedName("kontak_pabrik_pengolahan")
    val kontakPabrikPengolahan: String,
    @SerializedName("nama_pabrik_pengolahan")
    val namaPabrikPengolahan: String,
    @SerializedName("pabrik_pengolahan_id")
    val pabrikPengolahanId: Int,
    @SerializedName("timestamp")
    val timestamp: String
)