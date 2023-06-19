package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ItemDataInfoBinding
import com.wahyurhy.traceablegoods.model.datainfo.Item

class DataInfoAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<DataInfoAdapter.ViewHolder>() {

    var mDataInfo = ArrayList<Item>()
        set(mDataInfo) {
            if (mDataInfo.size > 0) {
                this.mDataInfo.clear()
            }
            this.mDataInfo.addAll(mDataInfo)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataInfoView = inflater.inflate(R.layout.item_data_info, parent, false)
        return ViewHolder(dataInfoView)
    }

    override fun getItemCount(): Int = mDataInfo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDataInfo[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDataInfoBinding.bind(itemView)
        fun bind(item: Item) {
            binding.dataCount.text = item.listData.size.toString()
            binding.dataName.text = item.dataName
            binding.cvItemDataInfo.setOnClickListener {
                onItemClickCallback.onItemClicked(item, adapterPosition)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedItem: Item?, position: Int?)
    }
}