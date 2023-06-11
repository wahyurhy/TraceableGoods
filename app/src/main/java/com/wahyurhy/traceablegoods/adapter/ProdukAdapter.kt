package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.produk.Item

class ProdukAdapter(private val mProduk: List<Item>) : RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val produkView = inflater.inflate(R.layout.item_produk, parent, false)
        return ViewHolder(produkView)
    }

    override fun getItemCount(): Int = mProduk.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produkModel = mProduk[position]
        val produk = holder.produk
        produk.text = produkModel.namaProduk
        val merek = holder.merek
        merek.text  = produkModel.merek
        val noLot = holder.noLot
        noLot.text = holder.itemView.context.getString(R.string.no_lot, produkModel.nomorLot)

        val image = holder.image
        when (produkModel.jenisProduk.lowercase()) {
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
        val produk = itemView.findViewById<TextView>(R.id.tv_produk)
        val merek = itemView.findViewById<TextView>(R.id.tv_merek)
        val noLot = itemView.findViewById<TextView>(R.id.tv_no_lot)
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