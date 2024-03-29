package com.wahyurhy.traceablegoods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.core.data.source.model.Produsen
import com.wahyurhy.traceablegoods.utils.Utils.NELAYAN
import com.wahyurhy.traceablegoods.utils.Utils.PETANI
import com.wahyurhy.traceablegoods.utils.Utils.PETERNAK

class ProdusenAdapter : RecyclerView.Adapter<ProdusenAdapter.ViewHolder>() {

    var mProdusen = ArrayList<Produsen>()
        set(mProdusen) {
            if (mProdusen.size > 0) {
                this.mProdusen.clear()
            }
            this.mProdusen.addAll(mProdusen)
        }

    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val produsenView = inflater.inflate(R.layout.item_produsen, parent, false)
        return ViewHolder(produsenView)
    }

    override fun getItemCount(): Int = mProdusen.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produsenModel = mProdusen[position]
        val produsen = holder.produsen
        produsen.text = produsenModel.namaProdusen
        val kategori = holder.kategori
        kategori.text  = produsenModel.kategoriProdusen
        val noNPWP = holder.noNPWP
        noNPWP.text = holder.itemView.context.getString(R.string.no_npwp, produsenModel.noNpwp)

        val image = holder.image
        when (produsenModel.kategoriProdusen.lowercase()) {
            PETANI -> image.setImageResource(R.drawable.ic_petani)
            NELAYAN -> image.setImageResource(R.drawable.ic_nelayan)
            PETERNAK -> image.setImageResource(R.drawable.ic_peternak)
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
        val produsen = itemView.findViewById<TextView>(R.id.tv_produsen_name)
        val kategori = itemView.findViewById<TextView>(R.id.tv_kategori)
        val noNPWP = itemView.findViewById<TextView>(R.id.tv_no_npwp)
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