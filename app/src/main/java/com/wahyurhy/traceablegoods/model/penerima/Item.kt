package com.wahyurhy.traceablegoods.model.penerima


import com.google.gson.annotations.SerializedName

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
    val penerimaId: Int
)