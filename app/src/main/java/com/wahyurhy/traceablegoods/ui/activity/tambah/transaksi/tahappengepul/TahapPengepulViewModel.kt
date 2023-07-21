package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappengepul

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.core.data.source.model.Distributor
import com.wahyurhy.traceablegoods.core.data.source.model.Pengepul
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapPengepulViewModel : ViewModel() {

    private var _pengepulList = MutableLiveData<ArrayList<Pengepul>>()
    val pengepulList: LiveData<ArrayList<Pengepul>> get() = _pengepulList

    private var _distributorList = MutableLiveData<ArrayList<Distributor>>()
    val distributorList: LiveData<ArrayList<Distributor>> get() = _distributorList

    private val _isTambahPengepulClicked = MutableLiveData<Boolean>()
    val isTambahPengepulClicked: LiveData<Boolean> = _isTambahPengepulClicked

    private val _isTambahDistributorClicked = MutableLiveData<Boolean>()
    val isTambahDistributorClicked: LiveData<Boolean> = _isTambahDistributorClicked

    fun loadDataPengepul(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllPengepul()
            val pengepul = MappingHelper.mapCursorToArrayListPengepul(cursor)
            _pengepulList.postValue(pengepul)
        }
    }

    fun loadDataDistributor(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllDistributor()
            val distributor = MappingHelper.mapCursorToArrayListDistributor(cursor)
            _distributorList.postValue(distributor)
        }
    }

    fun setTambahPengepulClicked(isClicked: Boolean) {
        _isTambahPengepulClicked.value = isClicked
    }

    fun setTambahDistributorClicked(isClicked: Boolean) {
        _isTambahDistributorClicked.value = isClicked
    }
}