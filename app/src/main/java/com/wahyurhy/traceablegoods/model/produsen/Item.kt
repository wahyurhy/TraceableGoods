package com.wahyurhy.traceablegoods.model.produsen


import com.google.gson.annotations.SerializedName

data class Item(
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
    val produsenId: Int
)