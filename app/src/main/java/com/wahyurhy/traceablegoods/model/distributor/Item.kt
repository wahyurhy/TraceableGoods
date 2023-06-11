package com.wahyurhy.traceablegoods.model.distributor


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("alamat_distributor")
    val alamatDistributor: String,
    @SerializedName("distributor_id")
    val distributorId: Int,
    @SerializedName("kontak_distributor")
    val kontakDistributor: String,
    @SerializedName("nama_distributor")
    val namaDistributor: String
)