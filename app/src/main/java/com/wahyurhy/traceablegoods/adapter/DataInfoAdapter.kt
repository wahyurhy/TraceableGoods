package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.DataInformasi
import com.wahyurhy.traceablegoods.utils.Utils

class DataInfoAdapter : RecyclerView.Adapter<DataInfoAdapter.ViewHolder>() {

    var mDataInfo = ArrayList<DataInformasi>()
        set(mDataInfo) {
            if (mDataInfo.size > 0) {
                this.mDataInfo.clear()
            }
            this.mDataInfo.addAll(mDataInfo)
            notifyDataSetChanged()
        }

    var produkCount = "0"
    var produsenCount = "0"
    var distributorCount = "0"
    var penerimaCount = "0"
    var penggilingCount = "0"
    var pengepulCount = "0"
    var gudangCount = "0"
    var tengkulakCount = "0"
    var pabrikPengolahanCount = "0"

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataInfoView = inflater.inflate(R.layout.item_data_info, parent, false)
        return ViewHolder(dataInfoView)
    }

    override fun getItemCount(): Int = mDataInfo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataInfo = mDataInfo[position]
        val dataName = holder.dataName
        dataName.text = dataInfo.dataName
        val dataCount = holder.dataCount
        when (dataInfo.dataName) {
            Utils.PRODUK -> {
                dataCount.text = produkCount
            }
            Utils.PRODUSEN -> {
                dataCount.text = produsenCount
            }
            Utils.DISTRIBUTOR -> {
                dataCount.text = distributorCount
            }
            Utils.PENERIMA -> {
                dataCount.text = penerimaCount
            }
            Utils.PENGGILING -> {
                dataCount.text = penggilingCount
            }
            Utils.PENGEPUL -> {
                dataCount.text = pengepulCount
            }
            Utils.GUDANG -> {
                dataCount.text = gudangCount
            }
            Utils.TENGKULAK -> {
                dataCount.text = tengkulakCount
            }
            Utils.PABRIK_PENGOLAHAN -> {
                dataCount.text = pabrikPengolahanCount
            }
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dataCount = itemView.findViewById<TextView>(R.id.data_count)
        val dataName = itemView.findViewById<TextView>(R.id.data_name)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position)
                }
            }
        }
    }
}