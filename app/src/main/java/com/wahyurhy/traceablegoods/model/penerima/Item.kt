package com.wahyurhy.traceablegoods.model.penerima


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("alamat_penerima")
    val alamatPenerima: String,
    @SerializedName("kategori_penerima")
    val kategoriPenerima: String,
    @SerializedName("kontak_penerima")
    val kontakPenerima: String,
    @SerializedName("nama_penerima")
    val namaPenerima: String,
    @SerializedName("penerima_id")
    val penerimaId: Int,
    @SerializedName("timestamp")
    val timestamp: String
): Parcelable