package com.wahyurhy.traceablegoods.core.data.source.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tengkulak(
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
) : Parcelable