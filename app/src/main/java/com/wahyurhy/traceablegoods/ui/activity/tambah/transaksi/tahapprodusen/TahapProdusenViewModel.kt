package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapprodusen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.Distributor
import com.wahyurhy.traceablegoods.model.Produk
import com.wahyurhy.traceablegoods.model.Produsen
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TahapProdusenViewModel : ViewModel() {

    private var _produsenList = MutableLiveData<ArrayList<Produsen>>()
    val produsenList: LiveData<ArrayList<Produsen>> get() = _produsenList

    private var _produkList = MutableLiveData<ArrayList<Produk>>()
    val produkList: LiveData<ArrayList<Produk>> get() = _produkList

    private var _distributorList = MutableLiveData<ArrayList<Distributor>>()
    val distributorList: LiveData<ArrayList<Distributor>> get() = _distributorList

    private val _isTambahProdusenClicked = MutableLiveData<Boolean>()
    val isTambahProdusenClicked: LiveData<Boolean> = _isTambahProdusenClicked

    private val _isTambahProdukClicked = MutableLiveData<Boolean>()

    val isTambahProdukClicked: LiveData<Boolean> = _isTambahProdukClicked

    private val _isTambahDistributorClicked = MutableLiveData<Boolean>()
    val isTambahDistributorClicked: LiveData<Boolean> = _isTambahDistributorClicked

    fun loadDataProdusen(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllProdusen()
            val produsen = MappingHelper.mapCursorToArrayListProdusen(cursor)
            _produsenList.postValue(produsen)
        }
    }

    fun loadDataProduk(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllProduk()
            val produk = MappingHelper.mapCursorToArrayListProduk(cursor)
            _produkList.postValue(produk)
        }
    }

    fun loadDataDistributor(traceableGoodHelper: TraceableGoodHelper) {
        viewModelScope.launch(Dispatchers.IO) {
            val cursor = traceableGoodHelper.queryAllDistributor()
            val distributor = MappingHelper.mapCursorToArrayListDistributor(cursor)
            _distributorList.postValue(distributor)
        }
    }

    fun setTambahProdusenClicked(isClicked: Boolean) {
        _isTambahProdusenClicked.value = isClicked
    }

    fun setTambahProdukClicked(isClicked: Boolean) {
        _isTambahProdukClicked.value = isClicked
    }

    fun setTambahDistributorClicked(isClicked: Boolean) {
        _isTambahDistributorClicked.value = isClicked
    }
}