package com.wahyurhy.traceablegoods.ui.fragment.transaksi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.adapter.TransaksiAdapter
import com.wahyurhy.traceablegoods.core.data.source.model.Transaksi
import com.wahyurhy.traceablegoods.databinding.FragmentTransaksiBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.TahapAlurDistribusiActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapgudang.TahapGudangActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappabrikpengolahan.TahapPabrikPengolahanActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenerima.TahapPenerimaActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappengepul.TahapPengepulActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenggiling.TahapPenggilingActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapprodusen.TahapProdusenActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahaptengkulak.TahapTengkulakActivity
import com.wahyurhy.traceablegoods.utils.MyDialogFilterTransaksiFragment
import com.wahyurhy.traceablegoods.utils.Prefs
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_BATCH_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK_BATCH_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_STATUS_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TRANSAKSI_ID

class TransaksiFragment : Fragment(), MyDialogFilterTransaksiFragment.FilterCallback {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private lateinit var adapter: TransaksiAdapter

    private var isFirstLaunched = false

    private lateinit var binding: FragmentTransaksiBinding

    private lateinit var viewModel: TransaksiViewModel

    private var batchId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TransaksiViewModel::class.java]

        traceableGoodHelper = TraceableGoodHelper.getInstance(requireActivity().applicationContext)
        traceableGoodHelper.open()

        setAdapter()

        initClickListener()
        hideFloatingActionButton()
        setSearchViewListener(traceableGoodHelper)
        setFilterData()

        batchId = arguments?.getString(EXTRA_BATCH_ID) ?: ""

        if (Prefs.isLogin)
            viewModel.loadDataTransaksi(traceableGoodHelper, batchId)
        else
            viewModel.filterDataTransaksi(traceableGoodHelper, "Selesai")

        // Observasi mTransaksi
        viewModel.transaksiList.observe(viewLifecycleOwner) { transaksiList ->
            setTransaksiData(transaksiList)
            if (batchId != "") {
                binding.rvTransaksi.post {
                    binding.rvTransaksi.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
                }
                batchId = ""
            }
        }
    }

    private fun setFilterData() {
        binding.btnFilter.setOnClickListener {
            val myDialogFilterTransaksiFragment = MyDialogFilterTransaksiFragment()
            myDialogFilterTransaksiFragment.setFilterCallback(this)
            myDialogFilterTransaksiFragment.show(
                childFragmentManager,
                myDialogFilterTransaksiFragment.tag
            )
        }
    }

    private fun setSearchViewListener(traceableGoodHelper: TraceableGoodHelper) {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != "") {
                    viewModel.loadDataTransaksi(traceableGoodHelper, newText.trim())
                } else {
                    if (Prefs.isLogin)
                        viewModel.loadDataTransaksi(traceableGoodHelper, batchId)
                    else
                        viewModel.filterDataTransaksi(traceableGoodHelper, "Selesai")
                }
                return true
            }
        })
    }

    private fun setTransaksiData(transaksi: ArrayList<Transaksi>) {
        if (transaksi.size > 0) {
            adapter.mTransaksi = transaksi

            adapter.setOnClickedListener(object : TransaksiAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    when (transaksi[position].status) {
                        getString(R.string.selesai) -> {
                            val intent =
                                Intent(requireContext(), TahapAlurDistribusiActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                                putExtra(EXTRA_STATUS_TRANSAKSI, transaksi[position].status)
                            }
                            startActivity(intent)
                        }
                        getString(R.string.gudang) -> {
                            val intent = Intent(requireContext(), TahapGudangActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                        getString(R.string.tengkulak) -> {
                            val intent =
                                Intent(requireContext(), TahapTengkulakActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                        getString(R.string.penggiling) -> {
                            val intent =
                                Intent(requireContext(), TahapPenggilingActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                        getString(R.string.pengepul) -> {
                            val intent = Intent(requireContext(), TahapPengepulActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                        getString(R.string.pabrik_pengolahan) -> {
                            val intent =
                                Intent(requireContext(), TahapPabrikPengolahanActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                        getString(R.string.penerima) -> {
                            val intent = Intent(requireContext(), TahapPenerimaActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                        else -> {
                            val intent = Intent(requireContext(), TahapProdusenActivity::class.java)
                            intent.apply {
                                putExtra(EXTRA_TRANSAKSI_ID, transaksi[position].transaksiId)
                                putExtra(EXTRA_BATCH_ID, transaksi[position].batchId)
                                putExtra(
                                    EXTRA_JENIS_PRODUK_TRANSAKSI,
                                    transaksi[position].jenisProduk
                                )
                                putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, transaksi[position].produk)
                                putExtra(
                                    EXTRA_PRODUK_BATCH_TRANSAKSI,
                                    transaksi[position].produkBatch
                                )
                            }
                            startActivity(intent)
                        }
                    }
                }
            })
        } else {
            adapter.mTransaksi = ArrayList()
            if (arguments != null) {
                Toast.makeText(requireContext(), "Data tidak ditemukan!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                    .show()
            }
            arguments = null
            if (Prefs.isLogin)
                viewModel.loadDataTransaksi(traceableGoodHelper, batchId)
            else
                viewModel.filterDataTransaksi(traceableGoodHelper, "Selesai")
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

    interface ScrollListener {
        fun onScrollChanged(scrollY: Int, recyclerView: RecyclerView)
    }

    private var scrollListener: ScrollListener? = null

    fun setScrollListener(listener: ScrollListener) {
        scrollListener = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        traceableGoodHelper.close()
        Prefs.apply {
            filterSemua = true
            filterSudahSelesai = false
            filterBelumSelesai = false
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLaunched) {
            adapter.mTransaksi = ArrayList()
            when {
                Prefs.filterSemua -> viewModel.loadDataTransaksi(traceableGoodHelper, batchId)
                Prefs.filterSudahSelesai -> viewModel.filterDataTransaksi(
                    traceableGoodHelper,
                    "Selesai"
                )
                Prefs.filterBelumSelesai -> {
                    if (Prefs.isLogin)
                        viewModel.filterDataTransaksi(traceableGoodHelper, "Belum Selesai")
                    else
                        Toast.makeText(
                            requireContext(),
                            "Maaf fitur filter \"Belum Selesai\" hanya untuk Admin saja",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
        isFirstLaunched = true
    }

    override fun onFilterChanged(
        filterSemua: Boolean,
        filterSudahSelesai: Boolean,
        filterBelumSelesai: Boolean
    ) {
        Prefs.filterSemua = filterSemua
        Prefs.filterSudahSelesai = filterSudahSelesai
        Prefs.filterBelumSelesai = filterBelumSelesai

        when {
            filterSemua -> viewModel.loadDataTransaksi(traceableGoodHelper, "")
            filterSudahSelesai -> viewModel.filterDataTransaksi(traceableGoodHelper, "Selesai")
            filterBelumSelesai -> {
                if (Prefs.isLogin)
                    viewModel.filterDataTransaksi(traceableGoodHelper, "Belum Selesai")
                else
                    Toast.makeText(
                        requireContext(),
                        "Filter \"Belum Selesai\" hanya untuk Admin saja",
                        Toast.LENGTH_SHORT
                    ).show()

            }
        }
    }

}