package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.core.data.source.model.Transaksi
import com.wahyurhy.traceablegoods.utils.Utils.BIJIAN
import com.wahyurhy.traceablegoods.utils.Utils.BUAHAN
import com.wahyurhy.traceablegoods.utils.Utils.DAGING_SAPI
import com.wahyurhy.traceablegoods.utils.Utils.DAGING_AYAM
import com.wahyurhy.traceablegoods.utils.Utils.GARAM
import com.wahyurhy.traceablegoods.utils.Utils.GULA
import com.wahyurhy.traceablegoods.utils.Utils.MENTEGA
import com.wahyurhy.traceablegoods.utils.Utils.MINYAK_GORENG
import com.wahyurhy.traceablegoods.utils.Utils.MINYAK_TANAH
import com.wahyurhy.traceablegoods.utils.Utils.SAYURAN
import com.wahyurhy.traceablegoods.utils.Utils.SUSU
import com.wahyurhy.traceablegoods.utils.Utils.TELUR

class TransaksiAdapter : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    var mTransaksi = ArrayList<Transaksi>()
        set(mTransaksi) {
            if (mTransaksi.size > 0) {
                this.mTransaksi.clear()
            }
            this.mTransaksi.addAll(mTransaksi)
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val transaksiView = inflater.inflate(R.layout.item_transaksi, parent, false)
        return ViewHolder(transaksiView)
    }

    override fun getItemCount(): Int = mTransaksi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaksi = mTransaksi[position]
        val produkBatch = holder.produkBatch
        produkBatch.text = transaksi.produkBatch
        val penerima = holder.penerima
        penerima.text  = transaksi.penerima
        val date = holder.date
        date.text = transaksi.date

        val image = holder.image
        when (transaksi.jenisProduk) {
            BIJIAN -> image.setImageResource(R.drawable.ic_bijian)
            BUAHAN -> image.setImageResource(R.drawable.ic_buahan)
            SAYURAN -> image.setImageResource(R.drawable.ic_sayuran)
            DAGING_SAPI -> image.setImageResource(R.drawable.ic_daging)
            GULA -> image.setImageResource(R.drawable.ic_gula)
            MINYAK_GORENG -> image.setImageResource(R.drawable.ic_minyak_goreng)
            MENTEGA -> image.setImageResource(R.drawable.ic_mentega)
            DAGING_AYAM -> image.setImageResource(R.drawable.ic_daging_ayam)
            TELUR -> image.setImageResource(R.drawable.ic_telur)
            SUSU -> image.setImageResource(R.drawable.ic_susu)
            GARAM -> image.setImageResource(R.drawable.ic_garam)
            MINYAK_TANAH -> image.setImageResource(R.drawable.ic_minyak_tanah)
        }

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val produkBatch = itemView.findViewById<TextView>(R.id.tv_produk_batch)
        val penerima = itemView.findViewById<TextView>(R.id.tv_penerima)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
        val image = itemView.findViewById<ImageView>(R.id.image_icon)

        val cardView = itemView.findViewById<CardView>(R.id.card_view)

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