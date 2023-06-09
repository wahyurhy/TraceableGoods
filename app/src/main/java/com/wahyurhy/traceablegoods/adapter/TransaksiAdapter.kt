package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.transaksi.Item

class TransaksiAdapter(private val mTransaksi: List<Item>) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

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
            "bijian" -> image.setImageResource(R.drawable.ic_bijian)
            "buahan" -> image.setImageResource(R.drawable.ic_buahan)
            "sayuran" -> image.setImageResource(R.drawable.ic_sayuran)
            "daging" -> image.setImageResource(R.drawable.ic_daging)
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