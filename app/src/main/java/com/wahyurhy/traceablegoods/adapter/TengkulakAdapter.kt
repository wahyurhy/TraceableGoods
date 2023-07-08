package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.tengkulak.Item

class TengkulakAdapter : RecyclerView.Adapter<TengkulakAdapter.ViewHolder>() {

    var mTengkulak = ArrayList<Item>()
        set(mTengkulak) {
            if (mTengkulak.size > 0) {
                this.mTengkulak.clear()
            }
            this.mTengkulak.addAll(mTengkulak)
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val tengkulakView = inflater.inflate(R.layout.item_tengkulak, parent, false)
        return ViewHolder(tengkulakView)
    }

    override fun getItemCount(): Int = mTengkulak.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tengkulakModel = mTengkulak[position]
        val tengkulak = holder.tengkulak
        tengkulak.text = tengkulakModel.namaTengkulak
        val alamat = holder.alamat
        alamat.text  = tengkulakModel.alamatTengkulak
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, tengkulakModel.kontakTengkulak)

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tengkulak = itemView.findViewById<TextView>(R.id.tv_tengkulak_name)
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