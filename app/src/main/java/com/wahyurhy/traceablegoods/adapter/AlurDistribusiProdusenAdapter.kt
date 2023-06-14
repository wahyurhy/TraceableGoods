package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.alurdistribusi.Detail

class AlurDistribusiProdusenAdapter(private val detail: Detail, private val tahap: String) : RecyclerView.Adapter<AlurDistribusiProdusenAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val alurDistribusiView = inflater.inflate(R.layout.item_alur_distribusi_produsen, parent, false)
        return ViewHolder(alurDistribusiView)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val produsen = holder.produsen
        produsen.text = tahap

        val nama = holder.nama
        nama.text  = detail.nama
        val produk = holder.produk
        produk.text = detail.produk
        val distributor = holder.distributor
        distributor.text = detail.distributor
        val totalDistribusi = holder.totalDistribusi
        totalDistribusi.text = detail.totalYangDidistribusikan
        val lokasiAsal = holder.lokasiAsal
        lokasiAsal.text = detail.lokasiAsal
        val lokasiTujuan = holder.lokasiTujuan
        lokasiTujuan.text = detail.lokasiTujuan
        val date = holder.date
        date.text = detail.date

    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val produsen = itemView.findViewById<TextView>(R.id.tv_produsen)
        val nama = itemView.findViewById<TextView>(R.id.tv_nama)
        val produk = itemView.findViewById<TextView>(R.id.tv_produk)
        val distributor = itemView.findViewById<TextView>(R.id.tv_distributor)
        val totalDistribusi = itemView.findViewById<TextView>(R.id.tv_total_yang_didistribusikan)
        val lokasiAsal = itemView.findViewById<TextView>(R.id.tv_lokasi_asal)
        val lokasiTujuan = itemView.findViewById<TextView>(R.id.tv_lokasi_tujuan)
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