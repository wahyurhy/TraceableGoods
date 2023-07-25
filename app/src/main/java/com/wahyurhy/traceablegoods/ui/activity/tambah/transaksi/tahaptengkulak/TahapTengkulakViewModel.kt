package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahaptengkulak

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.core.data.source.model.Distributor
import com.wahyurhy.traceablegoods.core.data.source.model.Tengkulak
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapTengkulakViewModel : ViewModel() {

    private var _tengkulakList = MutableLiveData<ArrayList<Tengkulak>>()
    val tengkulakList: LiveData<ArrayList<Tengkulak>> get() = _tengkulakList

    private var _distributorList = MutableLiveData<ArrayList<Distributor>>()
    val distributorList: LiveData<ArrayList<Distributor>> get() = _distributorList

    private val _isTambahTengkulakClicked = MutableLiveData<Boolean>()
    val isTambahTengkulakClicked: LiveData<Boolean> = _isTambahTengkulakClicked

    private val _isTambahDistributorClicked = MutableLiveData<Boolean>()
    val isTambahDistributorClicked: LiveData<Boolean> = _isTambahDistributorClicked

    fun loadDataTengkulak(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllTengkulak()
            val tengkulak = MappingHelper.mapCursorToArrayListTengkulak(cursor)
            _tengkulakList.postValue(tengkulak)
        }
    }

    fun loadDataDistributor(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllDistributor()
            val distributor = MappingHelper.mapCursorToArrayListDistributor(cursor)
            _distributorList.postValue(distributor)
        }
    }

    fun setTambahTengkulakClicked(isClicked: Boolean) {
        _isTambahTengkulakClicked.value = isClicked
    }

    fun setTambahDistributorClicked(isClicked: Boolean) {
        _isTambahDistributorClicked.value = isClicked
    }
}