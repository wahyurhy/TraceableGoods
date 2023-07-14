package com.wahyurhy.traceablegoods.utils

import android.app.Activity
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    const val PETANI = "petani"
    const val NELAYAN = "nelayan"
    const val PETERNAK = "peternak"

    const val BIJIAN = "Bijian"
    const val BUAHAN = "Buahan"
    const val SAYURAN = "Sayuran"
    const val DAGING = "Daging"

    const val PRODUK = "Produk"
    const val PRODUSEN = "Produsen"
    const val DISTRIBUTOR = "Distributor"
    const val PENERIMA = "Penerima"
    const val PENGGILING = "Penggiling"
    const val PENGEPUL = "Pengepul"
    const val GUDANG = "Gudang"
    const val TENGKULAK = "Tengkulak"
    const val PABRIK_PENGOLAHAN = "Pabrik Pengolahan"

    const val EXTRA_DATA_INFO = "extra_data_info"
    const val EXTRA_TRANSAKSI = "extra_transaksi"
    const val EXTRA_PRODUK = "extra_produk"
    const val EXTRA_PRODUSEN = "extra_produsen"
    const val EXTRA_DISTRIBUTOR = "extra_distributor"
    const val EXTRA_PENERIMA = "extra_penerima"
    const val EXTRA_PENGGILING = "extra_penggiling"
    const val EXTRA_PENGEPUL = "extra_pengepul"
    const val EXTRA_GUDANG = "extra_gudang"
    const val EXTRA_TENGKULAK = "extra_tengkulak"
    const val EXTRA_PABRIK_PENGOLAHAN = "extra_pabrik_pengolahan"
    const val EXTRA_TOTAL_DATA_INFO = "extra_total_data_info"
    const val EXTRA_PRODUK_ID = "extra_produk_id"
    const val EXTRA_JENIS_PRODUK = "extra_jenis_produk"
    const val EXTRA_NAMA_PRODUK = "extra_nama_produk"
    const val EXTRA_MEREK_PRODUK = "extra_merek_produk"
    const val EXTRA_NO_LOT_PRODUK = "extra_no_lot_produk"
    const val EXTRA_TGL_PRODUKSI_PRODUK = "extra_tgl_produksi_produk"
    const val EXTRA_TGL_KADALUARSA_PRODUK = "extra_tgl_kadaluarsa_produk"
    const val EXTRA_DESKRIPSI_PRODUK = "extra_deskripsi_produk"
    const val EXTRA_PRODUSEN_ID = "extra_produsen_id"
    const val EXTRA_KATEGORI_PRODUSEN = "extra_kategori_produsen"
    const val EXTRA_NAMA_PRODUSEN = "extra_nama_produsen"
    const val EXTRA_NPWP_PRODUSEN = "extra_npwp_produsen"
    const val EXTRA_KONTAK_PRODUSEN = "extra_kontak_produsen"
    const val EXTRA_ALAMAT_PRODUSEN = "extra_alamat_produsen"
    const val EXTRA_DISTRIBUTOR_ID = "extra_distributor_id"
    const val EXTRA_NAMA_DISTRIBUTOR = "extra_nama_distributor"
    const val EXTRA_KONTAK_DISTRIBUTOR = "extra_kontak_distributor"
    const val EXTRA_ALAMAT_DISTRIBUTOR = "extra_alamat_distributor"
    const val EXTRA_PENERIMA_ID = "extra_penerima_id"
    const val EXTRA_KATEGORI_PENERIMA = "extra_kategori_penerima"
    const val EXTRA_NAMA_PENERIMA = "extra_nama_penerima"
    const val EXTRA_KONTAK_PENERIMA = "extra_kontak_penerima"
    const val EXTRA_ALAMAT_PENERIMA = "extra_alamat_penerima"
    const val EXTRA_PENGGILING_ID = "extra_penggiling_id"
    const val EXTRA_NAMA_PENGGILING = "extra_nama_penggiling"
    const val EXTRA_KONTAK_PENGGILING = "extra_kontak_penggiling"
    const val EXTRA_ALAMAT_PENGGILING = "extra_alamat_penggiling"
    const val EXTRA_PENGEPUL_ID = "extra_pengepul_id"
    const val EXTRA_NAMA_PENGEPUL = "extra_nama_pengepul"
    const val EXTRA_KONTAK_PENGEPUL = "extra_kontak_pengepul"
    const val EXTRA_ALAMAT_PENGEPUL = "extra_alamat_pengepul"
    const val EXTRA_GUDANG_ID = "extra_gudang_id"
    const val EXTRA_NAMA_GUDANG = "extra_nama_gudang"
    const val EXTRA_KONTAK_GUDANG = "extra_kontak_gudang"
    const val EXTRA_ALAMAT_GUDANG = "extra_alamat_gudang"
    const val EXTRA_TENGKULAK_ID = "extra_tengkulak_id"
    const val EXTRA_NAMA_TENGKULAK = "extra_nama_tengkulak"
    const val EXTRA_KONTAK_TENGKULAK = "extra_kontak_tengkulak"
    const val EXTRA_ALAMAT_TENGKULAK = "extra_alamat_tengkulak"
    const val EXTRA_PABRIK_PENGOLAHAN_ID = "extra_pabrik_pengolahan_id"
    const val EXTRA_NAMA_PABRIK_PENGOLAHAN = "extra_nama_pabrik_pengolahan"
    const val EXTRA_KONTAK_PABRIK_PENGOLAHAN = "extra_kontak_pabrik_pengolahan"
    const val EXTRA_ALAMAT_PABRIK_PENGOLAHAN = "extra_alamat_pabrik_pengolahan"
    const val EXTRA_TRANSAKSI_ID = "transaksi_id"
    const val EXTRA_BATCH_ID = "batch_id"
    const val EXTRA_JENIS_PRODUK_TRANSAKSI = "jenis_produk"
    const val EXTRA_NAMA_PRODUK_TRANSAKSI = "nama_produk"
    const val EXTRA_PRODUK_BATCH_TRANSAKSI = "produk_batch"
    const val EXTRA_STATUS_TRANSAKSI = "status_extra"
    const val EXTRA_ALUR_PRODUSEN = "alur_produsen_extra"
    const val EXTRA_ALUR_DISTRIBUTOR= "alur_distributor_extra"
    const val EXTRA_ALUR_PENERIMA = "alur_penerima_extra"

    const val PRODUK_ID = 0
    const val PRODUSEN_ID = 1
    const val DISTRIBUTOR_ID = 2
    const val PENERIMA_ID = 3
    const val PENGGILING_ID = 4
    const val PENGEPUL_ID = 5
    const val GUDANG_ID = 6
    const val TENGKULAK_ID = 7
    const val PABRIK_PENGOLAHAN_ID = 8

    const val RESULT_ADD = 101
    const val RESULT_UPDATE = 201
    const val RESULT_DELETE = 301
    const val ALERT_DIALOG_CLOSE = 10
    const val ALERT_DIALOG_DELETE = 20
    fun setSystemBarFitWindow(activity: Activity) {
        activity.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    fun getCurrentDate(): String {
        val locale = Locale("id", "ID")
        val dateFormat = SimpleDateFormat("dd MMMM yyyy - HH:mm", locale)
        val date = Date()

        return dateFormat.format(date)
    }
}