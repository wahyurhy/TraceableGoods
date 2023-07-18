package com.wahyurhy.traceablegoods.ui.fragment.transaksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.Transaksi
import com.wahyurhy.traceablegoods.utils.MappingHelper
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
                    val cursor = traceableGoodHelper.queryTransaksiById(batchId)
                    MappingHelper.mapCursorToArrayListTransaksi(cursor)
                }
                _transaksiList.value = deferredTransaksi.await()
            } else {
                val deferredTransaksi = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllTransaksi()
                    MappingHelper.mapCursorToArrayListTransaksi(cursor)
                }
                _transaksiList.value = deferredTransaksi.await()
            }
        }
    }
}