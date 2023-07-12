package com.wahyurhy.traceablegoods.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.adapter.TransaksiAdapter
import com.wahyurhy.traceablegoods.databinding.FragmentTransaksiBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.transaksi.Item
import com.wahyurhy.traceablegoods.ui.activity.TahapAlurDistribusiActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.TahapProdusenActivity
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TRANSAKSI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TransaksiFragment : Fragment() {

    private lateinit var adapter: TransaksiAdapter

    private lateinit var binding: FragmentTransaksiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        initClickListener()
        hideFloatingActionButton()

        if (savedInstanceState == null) {
            // proses ambil data
            loadDataTransaksi()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Item>(EXTRA_TRANSAKSI)
            if (list != null) {
                adapter.mTransaksi = list
            }
        }
    }

    private fun loadDataTransaksi() {
        lifecycleScope.launch {
            binding.apply {
                val traceableGoodHelper = TraceableGoodHelper.getInstance(requireContext())
                traceableGoodHelper.open()

                val deferredTransaksi = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllTransaksi()
                    MappingHelper.mapCursorToArrayListTransaksi(cursor)
                }

                val transaksi = deferredTransaksi.await()

                if (transaksi.size > 0) {
                    adapter.mTransaksi = transaksi

                    adapter.setOnClickedListener(object : TransaksiAdapter.OnItemClickListener {
                        override fun onItemClick(itemView: View?, position: Int) {
                            val produkBatch = transaksi[position].produkBatch
                            Toast.makeText(
                                requireContext(),
                                "$produkBatch was clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                            if (transaksi[position].status == "selesai") {
                                val intent =
                                    Intent(requireContext(), TahapAlurDistribusiActivity::class.java)
                                intent.putExtra("batch_id", transaksi[position].batchId)
                                startActivity(intent)
                            } else {
                                val intent = Intent(requireContext(), TahapProdusenActivity::class.java)
                                intent.putExtra("batch_id", transaksi[position].batchId)
                                startActivity(intent)
                            }
                        }
                    })
                } else {
                    adapter.mTransaksi = ArrayList()
                    Toast.makeText(requireContext(), "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                        .show()
                }
                traceableGoodHelper.close()
            }
        }
    }

    private fun setAdapter() {
        adapter = TransaksiAdapter()
        binding.rvTransaksi.adapter = adapter
    }

    private fun hideFloatingActionButton() {
        binding.rvTransaksi.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollListener?.onScrollChanged(dy, recyclerView)
            }
        })
    }

    private fun initClickListener() {
        binding.btnFilter.setOnClickListener {
            Toast.makeText(requireContext(), "Filter CLicked!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_TRANSAKSI, adapter.mTransaksi)
    }

    interface ScrollListener {
        fun onScrollChanged(scrollY: Int, recyclerView: RecyclerView)
    }

    private var scrollListener: ScrollListener? = null

    fun setScrollListener(listener: ScrollListener) {
        scrollListener = listener
    }

}