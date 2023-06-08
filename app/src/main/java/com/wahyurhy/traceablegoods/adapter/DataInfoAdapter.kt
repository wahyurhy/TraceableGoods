package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.Item

class DataInfoAdapter(private val mDataInfo: List<Item>) : RecyclerView.Adapter<DataInfoAdapter.ViewHolder>() {

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
        val dataCount = holder.dataCount
        dataCount.text = dataInfo.dataCount
        val dataName = holder.dataName
        dataName.text  = dataInfo.dataName
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