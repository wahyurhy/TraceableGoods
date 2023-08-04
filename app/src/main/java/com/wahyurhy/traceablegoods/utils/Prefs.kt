package com.wahyurhy.traceablegoods.utils

import com.chibatching.kotpref.KotprefModel

object Prefs : KotprefModel() {

    var isLogin by booleanPref(false)
    var idAdmin by intPref(-1)
    var namaAdmin by stringPref("")
    var emailAdmin by stringPref("")
    var greeting by booleanPref(false)

    // filter transaksi
    var filterSemua by booleanPref(true)
    var filterSudahSelesai by booleanPref(false)
    var filterBelumSelesai by booleanPref(false)

    // preferensi
    var autoPrint by booleanPref(false)

}