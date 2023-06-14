package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.alurdistribusi.Detail

class AlurDistribusiPenerimaAdapter(private val detail: Detail, private val tahap: String) : RecyclerView.Adapter<AlurDistribusiPenerimaAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val alurDistribusiView = inflater.inflate(R.layout.item_alur_distribusi_penerima, parent, false)
        return ViewHolder(alurDistribusiView)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val produsen = holder.penerima
        produsen.text = tahap

        val kategori = holder.kategori
        kategori.text = detail.kategoriPanerima
        val nama = holder.nama
        nama.text  = detail.nama
        val totalDiterima = holder.totalDitarima
        totalDiterima.text = detail.totalYangDiterima
        val date = holder.date
        date.text = detail.date

    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val penerima = itemView.findViewById<TextView>(R.id.tv_penerima)
        val kategori = itemView.findViewById<TextView>(R.id.tv_kategori)
        val nama = itemView.findViewById<TextView>(R.id.tv_nama)
        val totalDitarima = itemView.findViewById<TextView>(R.id.tv_total_yang_diterima)
        val date = itemView.findViewById<TextView>(R.id.tv_date)


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