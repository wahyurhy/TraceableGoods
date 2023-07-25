package com.wahyurhy.traceablegoods.ui.fragment.transaksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.core.data.source.model.Transaksi
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TransaksiViewModel : ViewModel() {

    private val _transaksiList = MutableLiveData<ArrayList<Transaksi>>()
    val transaksiList: LiveData<ArrayList<Transaksi>> get() = _transaksiList

    fun loadDataTransaksi(traceableGoodHelper: TraceableGoodHelper, batchId: String) {
        viewModelScope.launch {
            if (batchId != "") {
                val deferredTransaksi = async(Dispatchers.IO) {
                    if (Prefs.isLogin) {
                        val cursor = traceableGoodHelper.queryTransaksiById(batchId)
                        MappingHelper.mapCursorToArrayListTransaksi(cursor)
                    } else {
                        val cursor = traceableGoodHelper.queryTransaksiByIdObserver(batchId)
                        MappingHelper.mapCursorToArrayListTransaksi(cursor)
                    }
                }
                _transaksiList.value = deferredTransaksi.await()
            } else {
                val deferredTransaksi = async(Dispatchers.IO) {
                    if (Prefs.isLogin) {
                        val cursor = traceableGoodHelper.queryAllTransaksi()
                        MappingHelper.mapCursorToArrayListTransaksi(cursor)
                    } else {
                        val cursor = traceableGoodHelper.queryTransaksiBySudah("Selesai")
                        MappingHelper.mapCursorToArrayListTransaksi(cursor)
                    }
                }
                _transaksiList.value = deferredTransaksi.await()
            }
        }
    }

    fun filterDataTransaksi(traceableGoodHelper: TraceableGoodHelper, status: String) {
        viewModelScope.launch {
            if (status == "Selesai") {
                val deferredTransaksi = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryTransaksiBySudah("Selesai")
                    MappingHelper.mapCursorToArrayListTransaksi(cursor)
                }
                _transaksiList.value = deferredTransaksi.await()
            } else {
                val deferredTransaksi = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryTransaksiByBelum("Selesai")
                    MappingHelper.mapCursorToArrayListTransaksi(cursor)
                }
                _transaksiList.value = deferredTransaksi.await()
            }
        }
    }
}