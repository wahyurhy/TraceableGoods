package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.core.data.source.model.AlurDistribusi

class AlurDistribusiPenerimaAdapter : RecyclerView.Adapter<AlurDistribusiPenerimaAdapter.ViewHolder>() {

    var mAlurDistribusi = ArrayList<AlurDistribusi>()
        set(mResult) {
            if (mResult.size > 0) {
                this.mAlurDistribusi.clear()
            }
            this.mAlurDistribusi.addAll(mResult)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val alurDistribusiView = inflater.inflate(R.layout.item_alur_distribusi_penerima, parent, false)
        return ViewHolder(alurDistribusiView)
    }

    override fun getItemCount(): Int = mAlurDistribusi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = mAlurDistribusi[position]
        val produsen = holder.penerima
        produsen.text = result.tahap

        val kategori = holder.kategori
        kategori.text = result.kategoriPanerima
        val nama = holder.nama
        nama.text  = result.nama
        val totalDiterima = holder.totalDitarima
        totalDiterima.text = result.totalYangDiterima
        val date = holder.date
        date.text = result.date

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val penerima = itemView.findViewById<TextView>(R.id.tv_penerima)
        val kategori = itemView.findViewById<TextView>(R.id.tv_kategori)
        val nama = itemView.findViewById<TextView>(R.id.tv_nama)
        val totalDitarima = itemView.findViewById<TextView>(R.id.tv_total_yang_diterima)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }
}