package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R

class DataInfoCardInfoAdapter : RecyclerView.Adapter<DataInfoCardInfoAdapter.ViewHolder>() {

    var totalDataInfo = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val dataInfoView = inflater.inflate(R.layout.item_card_info, parent, false)
        return ViewHolder(dataInfoView)
    }

    override fun getItemCount(): Int = totalDataInfo

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val infoContent = holder.infoContent
        infoContent.text = holder.infoContent.resources.getString(R.string.info_content, totalDataInfo.toString())
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val infoContent = itemView.findViewById<TextView>(R.id.tv_info_content)
    }
}