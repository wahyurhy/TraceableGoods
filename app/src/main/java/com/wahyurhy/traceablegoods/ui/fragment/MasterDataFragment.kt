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
import com.wahyurhy.traceablegoods.adapter.DataInfoAdapter
import com.wahyurhy.traceablegoods.adapter.DataInfoCardInfoAdapter
import com.wahyurhy.traceablegoods.databinding.FragmentMasterDataBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.datainfo.Item
import com.wahyurhy.traceablegoods.ui.activity.ListActivity
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DATA_INFO
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TOTAL_DATA_INFO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MasterDataFragment : Fragment() {

    private lateinit var binding: FragmentMasterDataBinding
    private lateinit var adapter: DataInfoAdapter
    private lateinit var adapterCardInfo: DataInfoCardInfoAdapter

    private var totalDataInfo = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMasterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        hideFloatingActionButton()

        if (savedInstanceState == null) {
            // proses ambil data
            loadDataInfoAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Item>(EXTRA_DATA_INFO)
            val totalDataInfoExtra = savedInstanceState.getInt(EXTRA_TOTAL_DATA_INFO)
            Toast.makeText(requireContext(), "hei $totalDataInfoExtra", Toast.LENGTH_SHORT).show()
            if (list != null) {
                adapter.mDataInfo = list
            }
            totalDataInfo = totalDataInfoExtra
        }
    }

    private fun setAdapter() {
        adapter = DataInfoAdapter()
        binding.rvDataInfo.adapter = adapter

        adapterCardInfo = DataInfoCardInfoAdapter()
        binding.rvCardInfo.adapter = adapterCardInfo
    }

    private fun loadDataInfoAsync() {
        lifecycleScope.launch {
            binding.apply {
                val traceableGoodHelper = TraceableGoodHelper.getInstance(requireActivity().applicationContext)
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
                    adapter.notifyDataSetChanged()
                    totalDataInfo = produk.size + produsen.size + distributor.size + penerima.size +
                            penggiling.size + pengepul.size + gudang.size + tengkulak.size + pabrikPengolahan.size

                    adapterCardInfo.totalDataInfo = totalDataInfo
                    adapterCardInfo.notifyDataSetChanged()
                    adapter.setOnClickedListener(object : DataInfoAdapter.OnItemClickListener {
                        override fun onItemClick(itemView: View?, position: Int) {
                            val dataName = dataInfo[position].dataName
                            Intent(requireContext(), ListActivity::class.java).apply {
                                putExtra(NAME_LIST, dataName)
                                startActivity(this)
                            }
                        }
                    })
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

    interface ScrollListener {
        fun onScrollChanged(scrollY: Int, oldScrollY: Int)
    }

    private var scrollListener: ScrollListener? = null

    fun setScrollListener(listener: ScrollListener) {
        scrollListener = listener
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_DATA_INFO, adapter.mDataInfo)
        outState.putInt(EXTRA_TOTAL_DATA_INFO, totalDataInfo)
    }

    companion object {
        const val NAME_LIST = "name"
    }

}