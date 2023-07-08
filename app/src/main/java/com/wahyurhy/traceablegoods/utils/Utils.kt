package com.wahyurhy.traceablegoods.utils

import android.app.Activity
import android.view.WindowManager

object Utils {

    const val PETANI = "petani"
    const val NELAYAN = "nelayan"
    const val PETERNAK = "peternak"

    const val BIJIAN = "bijian"
    const val BUAHAN = "buahan"
    const val SAYURAN = "sayuran"
    const val DAGING = "daging"

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
    const val EXTRA_PRODUK = "extra_produk"
    const val EXTRA_PRODUSEN = "extra_produsen"
    const val EXTRA_DISTRIBUTOR = "extra_distributor"
    const val EXTRA_PENERIMA = "extra_penerima"
    const val EXTRA_PENGGILING = "extra_penggiling"
    const val EXTRA_PENGEPUL = "extra_pengepul"
    const val EXTRA_GUDANG = "extra_gudang"
    const val EXTRA_TENGKULAK = "extra_tengkulak"
    const val EXTRA_PABRIK_PENGOLAHAN = "extra_pabrik_pengolahan"

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
}