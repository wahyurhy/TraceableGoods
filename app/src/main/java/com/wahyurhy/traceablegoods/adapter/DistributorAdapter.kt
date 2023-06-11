package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.model.distributor.Item

class DistributorAdapter(private val mDistributor: List<Item>) : RecyclerView.Adapter<DistributorAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val distributorView = inflater.inflate(R.layout.item_distributor, parent, false)
        return ViewHolder(distributorView)
    }

    override fun getItemCount(): Int = mDistributor.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val distributorModel = mDistributor[position]
        val distributor = holder.distributor
        distributor.text = distributorModel.namaDistributor
        val alamat = holder.alamat
        alamat.text  = distributorModel.alamatDistributor
        val kontak = holder.kontak
        kontak.text = holder.itemView.context.getString(R.string.kontak, distributorModel.kontakDistributor)

        val image = holder.image

        holder.cardView.viewTreeObserver.addOnGlobalLayoutListener {
            val height = holder.cardView.height
            holder.image.minimumHeight = height
        }
    }

    fun setOnClickedListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val distributor = itemView.findViewById<TextView>(R.id.tv_distributor_name)
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