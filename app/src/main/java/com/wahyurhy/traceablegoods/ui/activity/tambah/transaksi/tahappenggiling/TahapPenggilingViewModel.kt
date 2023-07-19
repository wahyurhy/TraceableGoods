package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenggiling

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.Distributor
import com.wahyurhy.traceablegoods.model.Penggiling
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapPenggilingViewModel : ViewModel() {

    private var _penggilingList = MutableLiveData<ArrayList<Penggiling>>()
    val penggilingList: LiveData<ArrayList<Penggiling>> get() = _penggilingList

    private var _distributorList = MutableLiveData<ArrayList<Distributor>>()
    val distributorList: LiveData<ArrayList<Distributor>> get() = _distributorList

    private val _isTambahPenggilingClicked = MutableLiveData<Boolean>()
    val isTambahPenggilingClicked: LiveData<Boolean> = _isTambahPenggilingClicked

    private val _isTambahDistributorClicked = MutableLiveData<Boolean>()
    val isTambahDistributorClicked: LiveData<Boolean> = _isTambahDistributorClicked

    fun loadDataPenggiling(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllPenggiling()
            val penggiling = MappingHelper.mapCursorToArrayListPenggiling(cursor)
            _penggilingList.postValue(penggiling)
        }
    }

    fun loadDataDistributor(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllDistributor()
            val distributor = MappingHelper.mapCursorToArrayListDistributor(cursor)
            _distributorList.postValue(distributor)
        }
    }

    fun setTambahPenggilingClicked(isClicked: Boolean) {
        _isTambahPenggilingClicked.value = isClicked
    }

    fun setTambahDistributorClicked(isClicked: Boolean) {
        _isTambahDistributorClicked.value = isClicked
    }
}