package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.penerima.Item

class PenerimaAdapter : RecyclerView.Adapter<PenerimaAdapter.ViewHolder>() {

    var mPenerima = ArrayList<Item>()
        set(mPenerima) {
            if (mPenerima.size > 0) {
                this.mPenerima.clear()
            }
            this.mPenerima.addAll(mPenerima)
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val penerimaView = inflater.inflate(R.layout.item_penerima, parent, false)
        return ViewHolder(penerimaView)
    }

    override fun getItemCount(): Int = mPenerima.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val penerimaModel = mPenerima[position]
        val penerima = holder.penerima
        penerima.text = penerimaModel.namaPenerima
        val kategori = holder.kategori
        kategori.text  = penerimaModel.kategoriPenerima
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, penerimaModel.kontakPenerima)

        val image = holder.image
        when (penerimaModel.kategoriPenerima) {
            "Pasar Induk" -> image.setImageResource(R.drawable.ic_pasar_induk)
            "Toko Grosir" -> image.setImageResource(R.drawable.ic_toko_grosir)
            "Toko / Pengecer" -> image.setImageResource(R.drawable.ic_toko_eceran)
            "Konsumen Akhir" -> image.setImageResource(R.drawable.ic_konsumen_akhir)
            "Restoran / Rumah Makan" -> image.setImageResource(R.drawable.ic_restoran_rumah_makan)
            "Instansi Pemerintah" -> image.setImageResource(R.drawable.ic_instansi_pemerintah)
            "Rumah Sakit / Fasilitas Kesehatan" -> image.setImageResource(R.drawable.ic_hospital)
            "Organisasi Kemanusiaan / Proyek Bantuan" -> image.setImageResource(R.drawable.ic_helper)
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
        val penerima = itemView.findViewById<TextView>(R.id.tv_penerima_name)
        val kategori = itemView.findViewById<TextView>(R.id.tv_kategori)
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