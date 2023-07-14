package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.Pengepul

class PengepulAdapter : RecyclerView.Adapter<PengepulAdapter.ViewHolder>() {

    var mPengepul = ArrayList<Pengepul>()
        set(mPengepul) {
            if (mPengepul.size > 0) {
                this.mPengepul.clear()
            }
            this.mPengepul.addAll(mPengepul)
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val pengepulView = inflater.inflate(R.layout.item_pengepul, parent, false)
        return ViewHolder(pengepulView)
    }

    override fun getItemCount(): Int = mPengepul.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pengepulModel = mPengepul[position]
        val pengepul = holder.pengepul
        pengepul.text = pengepulModel.namaPengepul
        val alamat = holder.alamat
        alamat.text  = pengepulModel.alamatPengepul
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, pengepulModel.kontakPengepul)

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pengepul = itemView.findViewById<TextView>(R.id.tv_pengepul_name)
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