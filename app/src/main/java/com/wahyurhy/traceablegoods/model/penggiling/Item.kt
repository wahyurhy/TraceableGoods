package com.wahyurhy.traceablegoods.model.penggiling


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable