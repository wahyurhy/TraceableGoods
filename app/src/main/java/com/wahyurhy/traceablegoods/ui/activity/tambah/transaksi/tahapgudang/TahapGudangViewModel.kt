package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapgudang

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.core.data.source.model.Distributor
import com.wahyurhy.traceablegoods.core.data.source.model.Gudang
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapGudangViewModel : ViewModel() {

    private var _gudangList = MutableLiveData<ArrayList<Gudang>>()
    val gudangList: LiveData<ArrayList<Gudang>> get() = _gudangList

    private var _distributorList = MutableLiveData<ArrayList<Distributor>>()
    val distributorList: LiveData<ArrayList<Distributor>> get() = _distributorList

    private val _isTambahGudangClicked = MutableLiveData<Boolean>()
    val isTambahGudangClicked: LiveData<Boolean> = _isTambahGudangClicked

    private val _isTambahDistributorClicked = MutableLiveData<Boolean>()
    val isTambahDistributorClicked: LiveData<Boolean> = _isTambahDistributorClicked

    fun loadDataGudang(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllGudang()
            val gudang = MappingHelper.mapCursorToArrayListGudang(cursor)
            _gudangList.postValue(gudang)
        }
    }

    fun loadDataDistributor(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllDistributor()
            val distributor = MappingHelper.mapCursorToArrayListDistributor(cursor)
            _distributorList.postValue(distributor)
        }
    }

    fun setTambahGudangClicked(isClicked: Boolean) {
        _isTambahGudangClicked.value = isClicked
    }

    fun setTambahDistributorClicked(isClicked: Boolean) {
        _isTambahDistributorClicked.value = isClicked
    }
}