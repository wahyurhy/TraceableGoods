package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.pabrikpengolahan.Item

class PabrikPengolahanAdapter(private val mPabrikPengolahan: List<Item>) : RecyclerView.Adapter<PabrikPengolahanAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val pabrikPengolahanView = inflater.inflate(R.layout.item_pabrik_pengolahan, parent, false)
        return ViewHolder(pabrikPengolahanView)
    }

    override fun getItemCount(): Int = mPabrikPengolahan.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pabrikPengolahanModel = mPabrikPengolahan[position]
        val pabrikPengolahan = holder.pabrikPengolahan
        pabrikPengolahan.text = pabrikPengolahanModel.namaPabrikPengolahan
        val alamat = holder.alamat
        alamat.text  = pabrikPengolahanModel.alamatPabrikPengolahan
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, pabrikPengolahanModel.kontakPabrikPengolahan)

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pabrikPengolahan = itemView.findViewById<TextView>(R.id.tv_pabrik_pengolahan_name)
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