package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.AlurDistribusi

class AlurDistribusiAdapter : RecyclerView.Adapter<AlurDistribusiAdapter.ViewHolder>() {

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
        val alurDistribusiView = inflater.inflate(R.layout.item_alur_distribusi, parent, false)
        return ViewHolder(alurDistribusiView)
    }

    override fun getItemCount(): Int = mAlurDistribusi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = mAlurDistribusi[position]
        val tahap = holder.tahap
        tahap.text = result.tahap

        val nama = holder.nama
        nama.text  = result.nama
        val distributor = holder.distributor
        distributor.text = result.distributor
        val totalYangDiterima = holder.totalYangDiterima
        totalYangDiterima.text = result.totalYangDiterima
        val totalDistribusi = holder.totalDistribusi
        totalDistribusi.text = result.totalYangDidistribusikan
        val lokasiAsal = holder.lokasiAsal
        lokasiAsal.text = result.lokasiAsal
        val lokasiTujuan = holder.lokasiTujuan
        lokasiTujuan.text = result.lokasiTujuan
        val date = holder.date
        date.text = result.date

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tahap = itemView.findViewById<TextView>(R.id.tv_tahap)
        val nama = itemView.findViewById<TextView>(R.id.tv_nama)
        val distributor = itemView.findViewById<TextView>(R.id.tv_distributor)
        val totalYangDiterima = itemView.findViewById<TextView>(R.id.tv_total_yang_diterima)
        val totalDistribusi = itemView.findViewById<TextView>(R.id.tv_total_yang_didistribusikan)
        val lokasiAsal = itemView.findViewById<TextView>(R.id.tv_lokasi_asal)
        val lokasiTujuan = itemView.findViewById<TextView>(R.id.tv_lokasi_tujuan)
        val date = itemView.findViewById<TextView>(R.id.tv_date)

    }
}