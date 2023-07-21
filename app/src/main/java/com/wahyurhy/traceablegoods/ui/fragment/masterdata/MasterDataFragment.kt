package com.wahyurhy.traceablegoods.ui.fragment.masterdata

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wahyurhy.traceablegoods.ViewModelFactory
import com.wahyurhy.traceablegoods.adapter.DataInfoAdapter
import com.wahyurhy.traceablegoods.adapter.DataInfoCardInfoAdapter
import com.wahyurhy.traceablegoods.databinding.FragmentMasterDataBinding
import com.wahyurhy.traceablegoods.ui.activity.ListActivity

class MasterDataFragment : Fragment() {

    private lateinit var binding: FragmentMasterDataBinding
    private lateinit var adapter: DataInfoAdapter
    private lateinit var adapterCardInfo: DataInfoCardInfoAdapter

    private lateinit var viewModel: MasterDataViewModel


    private var totalDataInfo = 0
    private var isFirstLaunched = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMasterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireActivity().application)
        )[MasterDataViewModel::class.java]

        setAdapter()
        hideFloatingActionButton()

        viewModel.loadDataInfo()

        // Observasi dataInfoList
        viewModel.dataInfoList.observe(viewLifecycleOwner) { dataInfoList ->
            // Lakukan tindakan yang sesuai dengan dataInfoList yang diperbarui
            adapter.mDataInfo = dataInfoList
            adapter.setOnClickedListener(object : DataInfoAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val dataName = dataInfoList[position].dataName
                    Intent(requireActivity(), ListActivity::class.java).apply {
                        putExtra(NAME_LIST, dataName)
                        startActivity(this)
                    }
                }
            })

            if (adapter.mDataInfo.size <= 0 || adapterCardInfo.totalDataInfo <= 0){
                binding.emptyNotification.visibility = View.VISIBLE
                binding.textEmptyNotification.visibility = View.VISIBLE
                binding.noData.visibility = View.VISIBLE
                binding.textNoData.visibility = View.VISIBLE
                binding.rvDataInfo.visibility = View.INVISIBLE
                binding.rvCardInfo.visibility = View.INVISIBLE
            }
            if (adapter.mDataInfo.size > 0 || adapterCardInfo.totalDataInfo > 0) {
                binding.emptyNotification.visibility = View.INVISIBLE
                binding.textEmptyNotification.visibility = View.INVISIBLE
                binding.noData.visibility = View.INVISIBLE
                binding.textNoData.visibility = View.INVISIBLE
                binding.rvDataInfo.visibility = View.VISIBLE
                binding.rvCardInfo.visibility = View.VISIBLE
            }
        }

        // Observasi perhitungan setiap data info
        viewModel.apply {
            produk.observe(viewLifecycleOwner) { produk ->
                adapter.produkCount = produk
                adapter.notifyDataSetChanged()
            }
            produsen.observe(viewLifecycleOwner) { produsen ->
                adapter.produsenCount = produsen
            }
            distributor.observe(viewLifecycleOwner) { distributor ->
                adapter.distributorCount = distributor
            }
            penerima.observe(viewLifecycleOwner) { penerima ->
                adapter.penerimaCount = penerima
            }
            penggiling.observe(viewLifecycleOwner) { penggiling ->
                adapter.penggilingCount = penggiling
            }
            pengepul.observe(viewLifecycleOwner) { pengepul ->
                adapter.pengepulCount = pengepul
            }
            gudang.observe(viewLifecycleOwner) { gudang ->
                adapter.gudangCount = gudang
            }
            tengkulak.observe(viewLifecycleOwner) { tengkulak ->
                adapter.tengkulakCount = tengkulak
            }
            pabrikPengolahan.observe(viewLifecycleOwner) { pabrikPengolahan ->
                adapter.pabrikPengolahanCount = pabrikPengolahan
            }
        }

        // Observasi totalDataInfo
        viewModel.totalDataInfo.observe(viewLifecycleOwner) { totalDataInfoViewModel ->
            totalDataInfo = totalDataInfoViewModel
            adapterCardInfo.totalDataInfo = totalDataInfo
            adapterCardInfo.notifyDataSetChanged()
        }

    }

    private fun setAdapter() {
        adapter = DataInfoAdapter()
        binding.rvDataInfo.adapter = adapter

        adapterCardInfo = DataInfoCardInfoAdapter()
        binding.rvCardInfo.adapter = adapterCardInfo
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

    override fun onResume() {
        super.onResume()
        if (isFirstLaunched) {
            adapter.mDataInfo = ArrayList()
            adapterCardInfo.totalDataInfo = 0
            viewModel.loadDataInfo()
        }
        isFirstLaunched = true
    }

    companion object {
        const val NAME_LIST = "name"
    }

}