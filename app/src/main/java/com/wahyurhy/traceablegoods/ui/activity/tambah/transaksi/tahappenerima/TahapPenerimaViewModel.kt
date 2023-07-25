package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenerima

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.core.data.source.model.Penerima
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapPenerimaViewModel : ViewModel() {

    private var _penerimaList = MutableLiveData<ArrayList<Penerima>>()
    val penerimaList: LiveData<ArrayList<Penerima>> get() = _penerimaList

    private val _isTambahPenerimaClicked = MutableLiveData<Boolean>()
    val isTambahPenerimaClicked: LiveData<Boolean> = _isTambahPenerimaClicked

    fun loadDataPenerima(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllPenerima()
            val penerima = MappingHelper.mapCursorToArrayListPenerima(cursor)
            _penerimaList.postValue(penerima)
        }
    }

    fun setTambahPenerimaClicked(isClicked: Boolean) {
        _isTambahPenerimaClicked.value = isClicked
    }
}