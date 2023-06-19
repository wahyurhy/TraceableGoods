package com.wahyurhy.traceablegoods.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.adapter.DataInfoAdapter
import com.wahyurhy.traceablegoods.adapter.DataInfoCardInfoAdapter
import com.wahyurhy.traceablegoods.databinding.FragmentMasterDataBinding
import com.wahyurhy.traceablegoods.model.datainfo.DataInfoModel
import com.wahyurhy.traceablegoods.model.datainfo.Item
import com.wahyurhy.traceablegoods.ui.activity.ListActivity
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

        adapter = DataInfoAdapter(object : DataInfoAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedItem: Item?, position: Int?) {

            }
        })
        binding.rvDataInfo.adapter = adapter

        hideFloatingActionButton()
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
//            val adapter = DataInfoAdapter(result.items)
            val adapterCardInfo = DataInfoCardInfoAdapter(result.items)
//            binding.rvDataInfo.adapter = adapter
            binding.rvCardInfo.adapter = adapterCardInfo
//            adapter.setOnClickedListener(object : DataInfoAdapter.OnItemClickListener {
//                override fun onItemClick(itemView: View?, position: Int) {
//                    val dataName = result.items[position].dataName
//                    Toast.makeText(requireContext(), "$dataName was clicked!", Toast.LENGTH_SHORT)
//                        .show()
//                    Intent(requireContext(), ListActivity::class.java).apply {
//                        putExtra(NAME_LIST, dataName)
//                        startActivity(this)
//                    }
//                }
//            })
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
    }

}