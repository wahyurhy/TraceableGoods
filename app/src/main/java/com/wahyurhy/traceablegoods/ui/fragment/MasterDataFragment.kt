package com.wahyurhy.traceablegoods.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.adapter.DataInfoAdapter
import com.wahyurhy.traceablegoods.adapter.DataInfoCardInfoAdapter
import com.wahyurhy.traceablegoods.databinding.FragmentMasterDataBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.datainfo.DataInfoModel
import com.wahyurhy.traceablegoods.model.datainfo.Item
import com.wahyurhy.traceablegoods.ui.activity.ListActivity
import com.wahyurhy.traceablegoods.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class MasterDataFragment : Fragment() {

    private lateinit var binding: FragmentMasterDataBinding
    private lateinit var adapter: DataInfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMasterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rawListInit()
        hideFloatingActionButton()

        if (savedInstanceState == null) {
            // proses ambil data
            loadDataInfoAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Item>(EXTRA_STATE)
            if (list != null) {
                adapter.mDataInfo = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.mDataInfo)
    }

    private fun loadDataInfoAsync() {
        lifecycleScope.launch {
            binding.apply {
                val traceableGoodHelper = TraceableGoodHelper.getInstance(requireContext())
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
                    adapter.apply {
                        mDataInfo = dataInfo
                        produkCount = produk.size.toString()
                        produsenCount = produsen.size.toString()
                        distributorCount = distributor.size.toString()
                        penerimaCount = penerima.size.toString()
                        penggilingCount = penggiling.size.toString()
                        pengepulCount = pengepul.size.toString()
                        gudangCount = gudang.size.toString()
                        tengkulakCount = tengkulak.size.toString()
                        pabrikPengolahanCount = pabrikPengolahan.size.toString()
                    }
                } else {
                    adapter.mDataInfo = ArrayList()
                    Toast.makeText(requireContext(), "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                        .show()
                }
                traceableGoodHelper.close()
            }
        }
    }

    private fun hideFloatingActionButton() {
        binding.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            scrollListener?.onScrollChanged(scrollY = scrollY, oldScrollY = oldScrollY)
        })
    }

    private fun rawListInit() {
        val gson = Gson()
        val i = requireContext().assets.open("data_info.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, DataInfoModel::class.java)

        bindData(dataList)
    }

    private fun bindData(dataList: DataInfoModel) {
        dataList.result.forEach { result ->
            adapter = DataInfoAdapter(result.items)
            val adapterCardInfo = DataInfoCardInfoAdapter(result.items)
            binding.rvDataInfo.adapter = adapter
            binding.rvCardInfo.adapter = adapterCardInfo
            adapter.setOnClickedListener(object : DataInfoAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val dataName = result.items[position].dataName
                    Toast.makeText(requireContext(), "$dataName was clicked!", Toast.LENGTH_SHORT)
                        .show()
                    Intent(requireContext(), ListActivity::class.java).apply {
                        putExtra(NAME_LIST, dataName)
                        startActivity(this)
                    }
                }
            })
        }
    }

    interface ScrollListener {
        fun onScrollChanged(scrollY: Int, oldScrollY: Int)
    }

    private var scrollListener: ScrollListener? = null

    fun setScrollListener(listener: ScrollListener) {
        scrollListener = listener
    }

    companion object {
        const val NAME_LIST = "name"
        private const val EXTRA_STATE = "extra_state"
    }

}