package com.wahyurhy.traceablegoods.model.distributor


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("alamat_distributor")
    val alamatDistributor: String,
    @SerializedName("distributor_id")
    val distributorId: Int,
    @SerializedName("kontak_distributor")
    val kontakDistributor: String,
    @SerializedName("nama_distributor")
    val namaDistributor: String,
    @SerializedName("timestamp")
    val timeStamp: String
): Parcelable