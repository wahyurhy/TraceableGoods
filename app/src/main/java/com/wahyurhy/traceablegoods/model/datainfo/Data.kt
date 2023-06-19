package com.wahyurhy.traceablegoods.model.datainfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("alamat_distributor")
    var alamatDistributor: String,
    @SerializedName("alamat_gudang")
    var alamatGudang: String,
    @SerializedName("alamat_pabrik_pengolahan")
    var alamatPabrikPengolahan: String,
    @SerializedName("alamat_penerima")
    var alamatPenerima: String,
    @SerializedName("alamat_pengepul")
    var alamatPengepul: String,
    @SerializedName("alamat_penggiling")
    var alamatPenggiling: String,
    @SerializedName("alamat_produsen")
    var alamatProdusen: String,
    @SerializedName("alamat_tengkulak")
    var alamatTengkulak: String,
    @SerializedName("deskripsi")
    var deskripsi: String,
    @SerializedName("distributor_id")
    var distributorId: Int,
    @SerializedName("gudang_id")
    var gudangId: Int,
    @SerializedName("jenis_produk")
    var jenisProduk: String,
    @SerializedName("kategori_penerima")
    var kategoriPenerima: String,
    @SerializedName("kategori_produsen")
    var kategoriProdusen: String,
    @SerializedName("kontak_distributor")
    var kontakDistributor: String,
    @SerializedName("kontak_gudang")
    var kontakGudang: String,
    @SerializedName("kontak_pabrik_pengolahan")
    var kontakPabrikPengolahan: String,
    @SerializedName("kontak_penerima")
    var kontakPenerima: String,
    @SerializedName("kontak_pengepul")
    var kontakPengepul: String,
    @SerializedName("kontak_penggiling")
    var kontakPenggiling: String,
    @SerializedName("kontak_produsen")
    var kontakProdusen: String,
    @SerializedName("kontak_tengkulak")
    var kontakTengkulak: String,
    @SerializedName("merek")
    var merek: String,
    @SerializedName("nama_distributor")
    var namaDistributor: String,
    @SerializedName("nama_gudang")
    var namaGudang: String,
    @SerializedName("nama_pabrik_pengolahan")
    var namaPabrikPengolahan: String,
    @SerializedName("nama_penerima")
    var namaPenerima: String,
    @SerializedName("nama_pengepul")
    var namaPengepul: String,
    @SerializedName("nama_penggiling")
    var namaPenggiling: String,
    @SerializedName("nama_produk")
    var namaProduk: String,
    @SerializedName("nama_produsen")
    var namaProdusen: String,
    @SerializedName("nama_tengkulak")
    var namaTengkulak: String,
    @SerializedName("no_npwp")
    var noNpwp: String,
    @SerializedName("nomor_lot")
    var nomorLot: String,
    @SerializedName("pabrik_pengolahan_id")
    var pabrikPengolahanId: Int,
    @SerializedName("penerima_id")
    var penerimaId: Int,
    @SerializedName("pengepul_id")
    var pengepulId: Int,
    @SerializedName("penggiling_id")
    var penggilingId: Int,
    @SerializedName("product_id")
    var productId: Int,
    @SerializedName("produsen_id")
    var produsenId: Int,
    @SerializedName("tanggal_kadaluarsa")
    var tanggalKadaluarsa: String,
    @SerializedName("tanggal_produksi")
    var tanggalProduksi: String,
    @SerializedName("tengkulak_id")
    var tengkulakId: Int,
    @SerializedName("timestamp")
    var timestamp: String
): Parcelable