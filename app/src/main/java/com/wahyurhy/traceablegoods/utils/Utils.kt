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
    fun setSystemBarFitWindow(activity: Activity) {
        activity.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
}