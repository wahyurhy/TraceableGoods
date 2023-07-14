package com.wahyurhy.traceablegoods.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gudang(
    @SerializedName("alamat_gudang")
    val alamatGudang: String,
    @SerializedName("gudang_id")
    val gudangId: Int,
    @SerializedName("kontak_gudang")
    val kontakGudang: String,
    @SerializedName("nama_gudang")
    val namaGudang: String,
    @SerializedName("timestamp")
    val timestamp: String
): Parcelable