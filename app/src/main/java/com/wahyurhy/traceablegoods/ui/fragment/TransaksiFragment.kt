package com.wahyurhy.traceablegoods.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.adapter.TransaksiAdapter
import com.wahyurhy.traceablegoods.databinding.FragmentTransaksiBinding
import com.wahyurhy.traceablegoods.model.transaksi.TransaksiModel
import java.io.BufferedReader
import java.io.InputStreamReader

class TransaksiFragment : Fragment() {

    private lateinit var binding: FragmentTransaksiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initClickListener()
        rawListInit()
        hideFloatingActionButton()
    }

    private fun hideFloatingActionButton() {
        binding.rvTransaksi.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollListener?.onScrollChanged(dy, recyclerView)
            }
        })
    }

    private fun initClickListener() {
        binding.btnFilter.setOnClickListener {
            Toast.makeText(requireContext(), "Filter CLicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun rawListInit() {
        val gson = Gson()
        val i = requireContext().assets.open("transaksi.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, TransaksiModel::class.java)

        bindData(dataList)
    }

    private fun bindData(dataList: TransaksiModel) {
        dataList.result.forEach { result ->
            val adapter = TransaksiAdapter(result.items)
            binding.rvTransaksi.adapter = adapter
            adapter.setOnClickedListener(object : TransaksiAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val produkBatch = result.items[position].produkBatch
                    Toast.makeText(requireContext(), "$produkBatch was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    interface ScrollListener {
        fun onScrollChanged(scrollY: Int, recyclerView: RecyclerView)
    }

    private var scrollListener: ScrollListener? = null

    fun setScrollListener(listener: ScrollListener) {
        scrollListener = listener
    }

}