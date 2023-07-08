package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.penggiling.Item

class PenggilingAdapter : RecyclerView.Adapter<PenggilingAdapter.ViewHolder>() {

    var mPenggiling = ArrayList<Item>()
        set(mPenggiling) {
            if (mPenggiling.size > 0) {
                this.mPenggiling.clear()
            }
            this.mPenggiling.addAll(mPenggiling)
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val penggilingView = inflater.inflate(R.layout.item_penggiling, parent, false)
        return ViewHolder(penggilingView)
    }

    override fun getItemCount(): Int = mPenggiling.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val penggilingModel = mPenggiling[position]
        val penggiling = holder.penggiling
        penggiling.text = penggilingModel.namaPenggiling
        val alamat = holder.alamat
        alamat.text  = penggilingModel.alamatPenggiling
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, penggilingModel.kontakPenggiling)

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val penggiling = itemView.findViewById<TextView>(R.id.tv_penggiling_name)
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