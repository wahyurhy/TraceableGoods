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

    const val PRODUK = "produk"
    const val PRODUSEN = "produsen"
    const val DISTRIBUTOR = "distributor"
    const val PENERIMA = "penerima"
    const val PENGGILING = "penggiling"
    const val PENGEPUL = "pengepul"
    const val GUDANG = "gudang"
    const val TENGKULAK = "tengkulak"
    const val PABRIK_PENGOLAHAN = "pabrik pengolahan"

    const val EXTRA_PRODUK = "extra_produk"
    const val EXTRA_PRODUSEN = "extra_produsen"
    const val EXTRA_DISTRIBUTOR = "extra_distributor"
    const val EXTRA_PENERIMA = "extra_penerima"
    const val EXTRA_PENGGILING = "extra_penggiling"
    const val EXTRA_PENGEPUL = "extra_pengepul"
    const val EXTRA_GUDANG = "extra_gudang"
    const val EXTRA_TENGKULAK = "extra_tengkulak"
    const val EXTRA_PABRIK_PENGOLAHAN = "extra_pabrik_pengolahan"

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