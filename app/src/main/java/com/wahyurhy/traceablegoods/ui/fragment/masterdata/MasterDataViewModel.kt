package com.wahyurhy.traceablegoods.ui.fragment.masterdata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.DataInformasi
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MasterDataViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _dataInfoList = MutableLiveData<ArrayList<DataInformasi>>()
    val dataInfoList: LiveData<ArrayList<DataInformasi>> get() = _dataInfoList

    private val _totalDataInfo = MutableLiveData<Int>()
    val totalDataInfo: LiveData<Int> get() = _totalDataInfo

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String> get() = _showToast

    private val _produk = MutableLiveData<String>()
    val produk: LiveData<String> get() = _produk

    private val _produsen = MutableLiveData<String>()
    val produsen: LiveData<String> get() = _produsen

    private val _distributor = MutableLiveData<String>()
    val distributor: LiveData<String> get() = _distributor

    private val _penerima = MutableLiveData<String>()
    val penerima: LiveData<String> get() = _penerima

    private val _penggiling = MutableLiveData<String>()
    val penggiling: LiveData<String> get() = _penggiling

    private val _pengepul = MutableLiveData<String>()
    val pengepul: LiveData<String> get() = _pengepul

    private val _gudang = MutableLiveData<String>()
    val gudang: LiveData<String> get() = _gudang

    private val _tengkulak = MutableLiveData<String>()
    val tengkulak: LiveData<String> get() = _tengkulak

    private val _pabrikPengolahan = MutableLiveData<String>()
    val pabrikPengolahan: LiveData<String> get() = _pabrikPengolahan

    fun loadDataInfo() {
        viewModelScope.launch {
            val traceableGoodHelper = TraceableGoodHelper.getInstance(application)
            traceableGoodHelper.open()

            val deferredDataInfo = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllDataInfo()
                MappingHelper.mapCursorToArrayListDataInfo(cursor)
            }
            val deferredProduk = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllProduk()
                MappingHelper.mapCursorToArrayListProduk(cursor)
            }
            val deferredProdusen = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllProdusen()
                MappingHelper.mapCursorToArrayListProdusen(cursor)
            }
            val deferredDistributor = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllDistributor()
                MappingHelper.mapCursorToArrayListDistributor(cursor)
            }
            val deferredPenerima = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllPenerima()
                MappingHelper.mapCursorToArrayListPenerima(cursor)
            }
            val deferredPenggiling = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllPenggiling()
                MappingHelper.mapCursorToArrayListPenggiling(cursor)
            }
            val deferredPengepul = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllPengepul()
                MappingHelper.mapCursorToArrayListPengepul(cursor)
            }
            val deferredGudang = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllGudang()
                MappingHelper.mapCursorToArrayListGudang(cursor)
            }
            val deferredTengkulak = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllTengkulak()
                MappingHelper.mapCursorToArrayListTengkulak(cursor)
            }
            val deferredPabrikPengolahan = async(Dispatchers.IO) {
                val cursor = traceableGoodHelper.queryAllPabrikPengolahan()
                MappingHelper.mapCursorToArrayListPabrikPengolahan(cursor)
            }

            val dataInfo = deferredDataInfo.await()
            val produk = deferredProduk.await()
            val produsen = deferredProdusen.await()
            val distributor = deferredDistributor.await()
            val penerima = deferredPenerima.await()
            val penggiling = deferredPenggiling.await()
            val pengepul = deferredPengepul.await()
            val gudang = deferredGudang.await()
            val tengkulak = deferredTengkulak.await()
            val pabrikPengolahan = deferredPabrikPengolahan.await()

            if (dataInfo.size > 0) {
                _dataInfoList.value = dataInfo
                _produk.value = produk.size.toString()
                _produsen.value = produsen.size.toString()
                _distributor.value = distributor.size.toString()
                _penerima.value = penerima.size.toString()
                _penggiling.value = penggiling.size.toString()
                _pengepul.value = pengepul.size.toString()
                _gudang.value = pengepul.size.toString()
                _tengkulak.value = tengkulak.size.toString()
                _pabrikPengolahan.value = pabrikPengolahan.size.toString()
                val totalDataInfo = produk.size + produsen.size + distributor.size + penerima.size +
                        penggiling.size + pengepul.size + gudang.size + tengkulak.size + pabrikPengolahan.size
                _totalDataInfo.value = totalDataInfo
            } else {
                _dataInfoList.value = ArrayList()
                _totalDataInfo.value = 0
                _showToast.value = "Tidak ada data saat ini"
            }

            traceableGoodHelper.close()
        }
    }
}