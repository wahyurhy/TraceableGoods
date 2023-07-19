package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappabrikpengolahan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.Distributor
import com.wahyurhy.traceablegoods.model.PabrikPengolahan
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapPabrikPengolahanViewModel : ViewModel() {

    private var _pabrikPengolahanList = MutableLiveData<ArrayList<PabrikPengolahan>>()
    val pabrikPengolahanList: LiveData<ArrayList<PabrikPengolahan>> get() = _pabrikPengolahanList

    private var _distributorList = MutableLiveData<ArrayList<Distributor>>()
    val distributorList: LiveData<ArrayList<Distributor>> get() = _distributorList

    private val _isTambahPabrikPengolahanClicked = MutableLiveData<Boolean>()
    val isTambahPabrikPengolahanClicked: LiveData<Boolean> = _isTambahPabrikPengolahanClicked

    private val _isTambahDistributorClicked = MutableLiveData<Boolean>()
    val isTambahDistributorClicked: LiveData<Boolean> = _isTambahDistributorClicked

    fun loadDataPabrikPengolahan(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllPabrikPengolahan()
            val pabrikPengolahan = MappingHelper.mapCursorToArrayListPabrikPengolahan(cursor)
            _pabrikPengolahanList.postValue(pabrikPengolahan)
        }
    }

    fun loadDataDistributor(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllDistributor()
            val distributor = MappingHelper.mapCursorToArrayListDistributor(cursor)
            _distributorList.postValue(distributor)
        }
    }

    fun setTambahPabrikPengolahanClicked(isClicked: Boolean) {
        _isTambahPabrikPengolahanClicked.value = isClicked
    }

    fun setTambahDistributorClicked(isClicked: Boolean) {
        _isTambahDistributorClicked.value = isClicked
    }
}