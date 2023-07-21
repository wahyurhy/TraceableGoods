package com.wahyurhy.traceablegoods.core.data.source.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produsen(
    @SerializedName("alamat_produsen")
    val alamatProdusen: String,
    @SerializedName("kategori_produsen")
    val kategoriProdusen: String,
    @SerializedName("kontak_produsen")
    val kontakProdusen: String,
    @SerializedName("nama_produsen")
    val namaProdusen: String,
    @SerializedName("no_npwp")
    val noNpwp: String,
    @SerializedName("produsen_id")
    val produsenId: Int,
    @SerializedName("timestamp")
    val timestamp: String
): Parcelable