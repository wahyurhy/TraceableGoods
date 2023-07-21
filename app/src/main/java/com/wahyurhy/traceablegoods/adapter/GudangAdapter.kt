package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.core.data.source.model.Gudang

class GudangAdapter : RecyclerView.Adapter<GudangAdapter.ViewHolder>() {

    var mGudang = ArrayList<Gudang>()
        set(mGudang) {
            if (mGudang.size > 0) {
                this.mGudang.clear()
            }
            this.mGudang.addAll(mGudang)
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val gudangView = inflater.inflate(R.layout.item_gudang, parent, false)
        return ViewHolder(gudangView)
    }

    override fun getItemCount(): Int = mGudang.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gudangModel = mGudang[position]
        val gudang = holder.gudang
        gudang.text = gudangModel.namaGudang
        val alamat = holder.alamat
        alamat.text  = gudangModel.alamatGudang
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, gudangModel.kontakGudang)

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gudang = itemView.findViewById<TextView>(R.id.tv_gudang_name)
        val alamat = itemView.findViewById<TextView>(R.id.tv_alamat)
        val kontak = itemView.findViewById<TextView>(R.id.tv_kontak)
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