package com.wahyurhy.traceablegoods.model.pabrikpengolahan


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable