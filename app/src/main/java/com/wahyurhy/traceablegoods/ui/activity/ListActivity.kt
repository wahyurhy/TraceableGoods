package com.wahyurhy.traceablegoods.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.adapter.*
import com.wahyurhy.traceablegoods.databinding.ActivityListBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.*
import com.wahyurhy.traceablegoods.ui.activity.detail.*
import com.wahyurhy.traceablegoods.ui.fragment.masterdata.MasterDataFragment.Companion.NAME_LIST
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_GUDANG
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_PENGEPUL
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_ALAMAT_TENGKULAK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DESKRIPSI_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DISTRIBUTOR_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_GUDANG
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_GUDANG_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KATEGORI_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KATEGORI_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_GUDANG
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_PENGEPUL
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_KONTAK_TENGKULAK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_MEREK_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_GUDANG
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PENGEPUL
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_TENGKULAK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NO_LOT_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NPWP_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PABRIK_PENGOLAHAN_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENERIMA_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGEPUL
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGEPUL_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGGILING_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUSEN_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TENGKULAK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TENGKULAK_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TGL_KADALUARSA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TGL_PRODUKSI_PRODUK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private lateinit var adapterProduk: ProdukAdapter
    private lateinit var adapterProdusen: ProdusenAdapter
    private lateinit var adapterDistributor: DistributorAdapter
    private lateinit var adapterPenerima: PenerimaAdapter
    private lateinit var adapterPenggiling: PenggilingAdapter
    private lateinit var adapterPengepul: PengepulAdapter
    private lateinit var adapterGudang: GudangAdapter
    private lateinit var adapterTengkulak: TengkulakAdapter
    private lateinit var adapterPabrikPengolahan: PabrikPengolahanAdapter

    private var nameList = ""

    private var isAdapterProdukClicked: Boolean = false
    private var isAdapterProdusenClicked: Boolean = false
    private var isAdapterDistributorClicked: Boolean = false
    private var isAdapterPenerimaClicked: Boolean = false
    private var isAdapterPenggilingClicked: Boolean = false
    private var isAdapterPengepulClicked: Boolean = false
    private var isAdapterGudangClicked: Boolean = false
    private var isAdapterTengkulakClicked: Boolean = false
    private var isAdapterPabrikPengolahanClicked: Boolean = false

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        nameList = intent.getStringExtra(NAME_LIST) ?: ""

        setAdapter(nameList)

        fitStatusBar()
        initExtras(nameList)
        initClickListener()
        setSearchViewListener(nameList, traceableGoodHelper)

        if (savedInstanceState == null) {
            // proses ambil data
            loadData(nameList)
        } else {
            when (nameList) {
                Utils.PRODUK -> {
                    val list = savedInstanceState.getParcelableArrayList<Produk>(EXTRA_PRODUK)
                    if (list != null) {
                        adapterProduk.mProduk = list
                    }
                }
                Utils.PRODUSEN -> {
                    val list = savedInstanceState.getParcelableArrayList<Produsen>(EXTRA_PRODUSEN)
                    if (list != null) {
                        adapterProdusen.mProdusen = list
                    }
                }
                Utils.DISTRIBUTOR -> {
                    val list = savedInstanceState.getParcelableArrayList<Distributor>(EXTRA_DISTRIBUTOR)
                    if (list != null) {
                        adapterDistributor.mDistributor = list
                    }
                }
                Utils.PENERIMA -> {
                    val list = savedInstanceState.getParcelableArrayList<Penerima>(EXTRA_PENERIMA)
                    if (list != null) {
                        adapterPenerima.mPenerima = list
                    }
                }
                Utils.PENGGILING -> {
                    val list = savedInstanceState.getParcelableArrayList<Penggiling>(EXTRA_PENGGILING)
                    if (list != null) {
                        adapterPenggiling.mPenggiling = list
                    }
                }
                Utils.PENGEPUL -> {
                    val list = savedInstanceState.getParcelableArrayList<Pengepul>(EXTRA_PENGEPUL)
                    if (list != null) {
                        adapterPengepul.mPengepul = list
                    }
                }
                Utils.GUDANG -> {
                    val list = savedInstanceState.getParcelableArrayList<Gudang>(EXTRA_GUDANG)
                    if (list != null) {
                        adapterGudang.mGudang = list
                    }
                }
                Utils.TENGKULAK -> {
                    val list = savedInstanceState.getParcelableArrayList<Tengkulak>(EXTRA_TENGKULAK)
                    if (list != null) {
                        adapterTengkulak.mTengkulak = list
                    }
                }
                Utils.PABRIK_PENGOLAHAN -> {
                    val list = savedInstanceState.getParcelableArrayList<PabrikPengolahan>(EXTRA_PABRIK_PENGOLAHAN)
                    if (list != null) {
                        adapterPabrikPengolahan.mPabrikPengolahan = list
                    }
                }
            }
        }
    }

    private fun setSearchViewListener(nameList: String, traceableGoodHelper: TraceableGoodHelper) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != "") {
                    binding.apply {
                        when (nameList) {
                            Utils.PRODUK -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryProdukByName(newText.trim())
                                val produkCursor = MappingHelper.mapCursorToArrayListProduk(cursor)
                                val produk = produkCursor
                                setProdukData(produk)
                            }
                            Utils.PRODUSEN -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryProdusenByName(newText.trim())
                                val produsenCursor = MappingHelper.mapCursorToArrayListProdusen(cursor)
                                val produsen = produsenCursor
                                setProdusenData(produsen)
                            }
                            Utils.DISTRIBUTOR -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryDistributorByName(newText.trim())
                                val distributorCursor = MappingHelper.mapCursorToArrayListDistributor(cursor)
                                val distributor = distributorCursor
                                setDistributorData(distributor)
                            }
                            Utils.PENERIMA -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryPenerimaByName(newText.trim())
                                val penerimaCursor = MappingHelper.mapCursorToArrayListPenerima(cursor)
                                val penerima = penerimaCursor
                                setPenerimaData(penerima)
                            }
                            Utils.PENGGILING -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryPenggilingByName(newText.trim())
                                val penggilingCursor = MappingHelper.mapCursorToArrayListPenggiling(cursor)
                                val penggiling = penggilingCursor
                                setPenggilingData(penggiling)
                            }
                            Utils.PENGEPUL -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryPengepulByName(newText.trim())
                                val pengepulCursor = MappingHelper.mapCursorToArrayListPengepul(cursor)
                                val pengepul = pengepulCursor
                                setPengepulData(pengepul)
                            }
                            Utils.GUDANG -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryGudangByName(newText.trim())
                                val gudangCursor = MappingHelper.mapCursorToArrayListGudang(cursor)
                                val gudang = gudangCursor
                                setGudangData(gudang)
                            }
                            Utils.TENGKULAK -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryTengkulakByName(newText.trim())
                                val tengkulakCursor = MappingHelper.mapCursorToArrayListTengkulak(cursor)
                                val tengkulak = tengkulakCursor
                                setTengkulakData(tengkulak)
                            }
                            Utils.PABRIK_PENGOLAHAN -> {
                                val cursor = this@ListActivity.traceableGoodHelper.queryPabrikPengolahanByName(newText.trim())
                                val pabrikPengolahanCursor = MappingHelper.mapCursorToArrayListPabrikPengolahan(cursor)
                                val pabrikPengolahan = pabrikPengolahanCursor
                                setPabrikPengolahanData(pabrikPengolahan)
                            }
                        }
                    }
                } else {
                    loadData(nameList)
                }
                return true
            }
        })
    }

    private fun setAdapter(nameList: String) {
        when (nameList) {
            Utils.PRODUK -> {
                adapterProduk = ProdukAdapter()
                binding.rvList.adapter = adapterProduk
            }
            Utils.PRODUSEN -> {
                adapterProdusen = ProdusenAdapter()
                binding.rvList.adapter = adapterProdusen
            }
            Utils.DISTRIBUTOR -> {
                adapterDistributor = DistributorAdapter()
                binding.rvList.adapter = adapterDistributor
            }
            Utils.PENERIMA -> {
                adapterPenerima = PenerimaAdapter()
                binding.rvList.adapter = adapterPenerima
            }
            Utils.PENGGILING -> {
                adapterPenggiling = PenggilingAdapter()
                binding.rvList.adapter = adapterPenggiling
            }
            Utils.PENGEPUL -> {
                adapterPengepul = PengepulAdapter()
                binding.rvList.adapter = adapterPengepul
            }
            Utils.GUDANG -> {
                adapterGudang = GudangAdapter()
                binding.rvList.adapter = adapterGudang
            }
            Utils.TENGKULAK -> {
                adapterTengkulak = TengkulakAdapter()
                binding.rvList.adapter = adapterTengkulak
            }
            Utils.PABRIK_PENGOLAHAN -> {
                adapterPabrikPengolahan = PabrikPengolahanAdapter()
                binding.rvList.adapter = adapterPabrikPengolahan
            }
        }
    }

    private fun loadData(nameList: String) {
        lifecycleScope.launch {
            binding.apply {
                when (nameList) {
                    Utils.PRODUK -> {
                        val deferredProduk = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllProduk()
                            MappingHelper.mapCursorToArrayListProduk(cursor)
                        }
                        val produk = deferredProduk.await()
                        setProdukData(produk)
                    }
                    Utils.PRODUSEN -> {
                        val deferredProdusen = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllProdusen()
                            MappingHelper.mapCursorToArrayListProdusen(cursor)
                        }
                        val produsen = deferredProdusen.await()
                        setProdusenData(produsen)
                    }
                    Utils.DISTRIBUTOR -> {
                        val deferredDistributor = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllDistributor()
                            MappingHelper.mapCursorToArrayListDistributor(cursor)
                        }
                        val distributor = deferredDistributor.await()
                        setDistributorData(distributor)
                    }
                    Utils.PENERIMA -> {
                        val deferredPenerima = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllPenerima()
                            MappingHelper.mapCursorToArrayListPenerima(cursor)
                        }
                        val penerima = deferredPenerima.await()
                        setPenerimaData(penerima)
                    }
                    Utils.PENGGILING -> {
                        val deferredPenggiling = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllPenggiling()
                            MappingHelper.mapCursorToArrayListPenggiling(cursor)
                        }
                        val penggiling = deferredPenggiling.await()
                        setPenggilingData(penggiling)
                    }
                    Utils.PENGEPUL -> {
                        val deferredPengepul = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllPengepul()
                            MappingHelper.mapCursorToArrayListPengepul(cursor)
                        }
                        val pengepul = deferredPengepul.await()
                        setPengepulData(pengepul)
                    }
                    Utils.GUDANG -> {
                        val deferredGudang = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllGudang()
                            MappingHelper.mapCursorToArrayListGudang(cursor)
                        }
                        val gudang = deferredGudang.await()
                        setGudangData(gudang)
                    }
                    Utils.TENGKULAK -> {
                        val deferredTengkulak = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllTengkulak()
                            MappingHelper.mapCursorToArrayListTengkulak(cursor)
                        }
                        val tengkulak = deferredTengkulak.await()
                        setTengkulakData(tengkulak)
                    }
                    Utils.PABRIK_PENGOLAHAN -> {
                        val deferredPabrikPengolahan = async(Dispatchers.IO) {
                            val cursor = this@ListActivity.traceableGoodHelper.queryAllPabrikPengolahan()
                            MappingHelper.mapCursorToArrayListPabrikPengolahan(cursor)
                        }
                        val pabrikPengolahan = deferredPabrikPengolahan.await()
                        setPabrikPengolahanData(pabrikPengolahan)
                    }
                }
            }
        }
    }

    private fun setPabrikPengolahanData(pabrikPengolahan: ArrayList<PabrikPengolahan>) {
        if (pabrikPengolahan.size > 0) {
            adapterPabrikPengolahan.mPabrikPengolahan = pabrikPengolahan
            adapterPabrikPengolahan.notifyDataSetChanged()
            adapterPabrikPengolahan.setOnClickedListener(object :
                PabrikPengolahanAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailPabrikPengolahan =
                        Intent(this@ListActivity, DetailPabrikPengolahanActivity::class.java)
                    intentDetailPabrikPengolahan.apply {
                        putExtra(
                            EXTRA_PABRIK_PENGOLAHAN_ID,
                            pabrikPengolahan[position].pabrikPengolahanId
                        )
                        putExtra(
                            EXTRA_NAMA_PABRIK_PENGOLAHAN,
                            pabrikPengolahan[position].namaPabrikPengolahan
                        )
                        putExtra(
                            EXTRA_KONTAK_PABRIK_PENGOLAHAN,
                            pabrikPengolahan[position].kontakPabrikPengolahan
                        )
                        putExtra(
                            EXTRA_ALAMAT_PABRIK_PENGOLAHAN,
                            pabrikPengolahan[position].alamatPabrikPengolahan
                        )
                    }
                    startActivity(intentDetailPabrikPengolahan)
                    isAdapterProdukClicked = true
                }
            })
        } else {
            adapterPabrikPengolahan.mPabrikPengolahan = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setTengkulakData(tengkulak: ArrayList<Tengkulak>) {
        if (tengkulak.size > 0) {
            adapterTengkulak.mTengkulak = tengkulak
            adapterTengkulak.notifyDataSetChanged()
            adapterTengkulak.setOnClickedListener(object : TengkulakAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailTengkulak =
                        Intent(this@ListActivity, DetailTengkulakActivity::class.java)
                    intentDetailTengkulak.apply {
                        putExtra(EXTRA_TENGKULAK_ID, tengkulak[position].tengkulakId)
                        putExtra(EXTRA_NAMA_TENGKULAK, tengkulak[position].namaTengkulak)
                        putExtra(EXTRA_KONTAK_TENGKULAK, tengkulak[position].kontakTengkulak)
                        putExtra(EXTRA_ALAMAT_TENGKULAK, tengkulak[position].alamatTengkulak)
                    }
                    startActivity(intentDetailTengkulak)
                    isAdapterTengkulakClicked = true
                }
            })
        } else {
            adapterTengkulak.mTengkulak = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setGudangData(gudang: ArrayList<Gudang>) {
        if (gudang.size > 0) {
            adapterGudang.mGudang = gudang
            adapterGudang.notifyDataSetChanged()
            adapterGudang.setOnClickedListener(object : GudangAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailGudang =
                        Intent(this@ListActivity, DetailGudangActivity::class.java)
                    intentDetailGudang.apply {
                        putExtra(EXTRA_GUDANG_ID, gudang[position].gudangId)
                        putExtra(EXTRA_NAMA_GUDANG, gudang[position].namaGudang)
                        putExtra(EXTRA_KONTAK_GUDANG, gudang[position].kontakGudang)
                        putExtra(EXTRA_ALAMAT_GUDANG, gudang[position].alamatGudang)
                    }
                    startActivity(intentDetailGudang)
                    isAdapterGudangClicked = true
                }
            })
        } else {
            adapterGudang.mGudang = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setPengepulData(pengepul: ArrayList<Pengepul>) {
        if (pengepul.size > 0) {
            adapterPengepul.mPengepul = pengepul
            adapterPengepul.notifyDataSetChanged()
            adapterPengepul.setOnClickedListener(object : PengepulAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailPengepul =
                        Intent(this@ListActivity, DetailPengepulActivity::class.java)
                    intentDetailPengepul.apply {
                        putExtra(EXTRA_PENGEPUL_ID, pengepul[position].pengepulId)
                        putExtra(EXTRA_NAMA_PENGEPUL, pengepul[position].namaPengepul)
                        putExtra(EXTRA_KONTAK_PENGEPUL, pengepul[position].kontakPengepul)
                        putExtra(EXTRA_ALAMAT_PENGEPUL, pengepul[position].alamatPengepul)
                    }
                    startActivity(intentDetailPengepul)
                    isAdapterPengepulClicked = true
                }
            })
        } else {
            adapterPengepul.mPengepul = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setPenggilingData(penggiling: ArrayList<Penggiling>) {
        if (penggiling.size > 0) {
            adapterPenggiling.mPenggiling = penggiling
            adapterPenggiling.notifyDataSetChanged()
            adapterPenggiling.setOnClickedListener(object : PenggilingAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailPenggiling =
                        Intent(this@ListActivity, DetailPenggilingActivity::class.java)
                    intentDetailPenggiling.apply {
                        putExtra(EXTRA_PENGGILING_ID, penggiling[position].penggilingId)
                        putExtra(EXTRA_NAMA_PENGGILING, penggiling[position].namaPenggiling)
                        putExtra(EXTRA_KONTAK_PENGGILING, penggiling[position].kontakPenggiling)
                        putExtra(EXTRA_ALAMAT_PENGGILING, penggiling[position].alamatPenggiling)
                    }
                    startActivity(intentDetailPenggiling)
                    isAdapterPenggilingClicked = true
                }
            })
        } else {
            adapterPenggiling.mPenggiling = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setPenerimaData(penerima: ArrayList<Penerima>) {
        if (penerima.size > 0) {
            adapterPenerima.mPenerima = penerima
            adapterPenerima.notifyDataSetChanged()
            adapterPenerima.setOnClickedListener(object : PenerimaAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailPenerima =
                        Intent(this@ListActivity, DetailPenerimaActivity::class.java)
                    intentDetailPenerima.apply {
                        putExtra(EXTRA_PENERIMA_ID, penerima[position].penerimaId)
                        putExtra(EXTRA_KATEGORI_PENERIMA, penerima[position].kategoriPenerima)
                        putExtra(EXTRA_NAMA_PENERIMA, penerima[position].namaPenerima)
                        putExtra(EXTRA_KONTAK_PENERIMA, penerima[position].kontakPenerima)
                        putExtra(EXTRA_ALAMAT_PENERIMA, penerima[position].alamatPenerima)
                    }
                    startActivity(intentDetailPenerima)
                    isAdapterPenerimaClicked = true
                }
            })
        } else {
            adapterPenerima.mPenerima = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setDistributorData(distributor: ArrayList<Distributor>) {
        if (distributor.size > 0) {
            adapterDistributor.mDistributor = distributor
            adapterDistributor.notifyDataSetChanged()
            adapterDistributor.setOnClickedListener(object :
                DistributorAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailDistributor =
                        Intent(this@ListActivity, DetailDistributorActivity::class.java)
                    intentDetailDistributor.apply {
                        putExtra(EXTRA_DISTRIBUTOR_ID, distributor[position].distributorId)
                        putExtra(EXTRA_NAMA_DISTRIBUTOR, distributor[position].namaDistributor)
                        putExtra(EXTRA_KONTAK_DISTRIBUTOR, distributor[position].kontakDistributor)
                        putExtra(EXTRA_ALAMAT_DISTRIBUTOR, distributor[position].alamatDistributor)
                    }
                    startActivity(intentDetailDistributor)
                    isAdapterDistributorClicked = true
                }
            })
        } else {
            adapterDistributor.mDistributor = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setProdusenData(produsen: ArrayList<Produsen>) {
        if (produsen.size > 0) {
            adapterProdusen.mProdusen = produsen
            adapterProdusen.notifyDataSetChanged()
            adapterProdusen.setOnClickedListener(object : ProdusenAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailProdusen =
                        Intent(this@ListActivity, DetailProdusenActivity::class.java)
                    intentDetailProdusen.apply {
                        putExtra(EXTRA_PRODUSEN_ID, produsen[position].produsenId)
                        putExtra(EXTRA_KATEGORI_PRODUSEN, produsen[position].kategoriProdusen)
                        putExtra(EXTRA_NAMA_PRODUSEN, produsen[position].namaProdusen)
                        putExtra(EXTRA_NPWP_PRODUSEN, produsen[position].noNpwp)
                        putExtra(EXTRA_KONTAK_PRODUSEN, produsen[position].kontakProdusen)
                        putExtra(EXTRA_ALAMAT_PRODUSEN, produsen[position].alamatProdusen)
                    }
                    startActivity(intentDetailProdusen)
                    isAdapterProdusenClicked = true
                }
            })
        } else {
            adapterProdusen.mProdusen = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setProdukData(produk: ArrayList<Produk>) {
        if (produk.size > 0) {
            adapterProduk.mProduk = produk
            adapterProduk.notifyDataSetChanged()
            adapterProduk.setOnClickedListener(object : ProdukAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val intentDetailProduk =
                        Intent(this@ListActivity, DetailProdukActivity::class.java)
                    intentDetailProduk.apply {
                        putExtra(EXTRA_PRODUK_ID, produk[position].productId)
                        putExtra(EXTRA_JENIS_PRODUK, produk[position].jenisProduk)
                        putExtra(EXTRA_NAMA_PRODUK, produk[position].namaProduk)
                        putExtra(EXTRA_MEREK_PRODUK, produk[position].merek)
                        putExtra(EXTRA_NO_LOT_PRODUK, produk[position].nomorLot)
                        putExtra(EXTRA_TGL_PRODUKSI_PRODUK, produk[position].tanggalProduksi)
                        putExtra(EXTRA_TGL_KADALUARSA_PRODUK, produk[position].tanggalKadaluarsa)
                        putExtra(EXTRA_DESKRIPSI_PRODUK, produk[position].deskripsi)
                    }
                    startActivity(intentDetailProduk)
                    isAdapterProdukClicked = true
                }
            })
        } else {
            adapterProduk.mProduk = ArrayList()
            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initExtras(nameList: String) {
        binding.tvListTitle.text = getString(R.string.list_title, nameList)
        binding.searchView.queryHint = getString(R.string.cari_apa, nameList)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        when (nameList) {
            Utils.PRODUK -> {
                outState.putParcelableArrayList(EXTRA_PRODUK, adapterProduk.mProduk)
            }
            Utils.PRODUSEN -> {
                outState.putParcelableArrayList(EXTRA_PRODUSEN, adapterProdusen.mProdusen)
            }
            Utils.DISTRIBUTOR -> {
                outState.putParcelableArrayList(EXTRA_DISTRIBUTOR, adapterDistributor.mDistributor)
            }
            Utils.PENERIMA -> {
                outState.putParcelableArrayList(EXTRA_PENERIMA, adapterPenerima.mPenerima)
            }
            Utils.PENGGILING -> {
                outState.putParcelableArrayList(EXTRA_PENGGILING, adapterPenggiling.mPenggiling)
            }
            Utils.PENGEPUL -> {
                outState.putParcelableArrayList(EXTRA_PENGEPUL, adapterPengepul.mPengepul)
            }
            Utils.GUDANG -> {
                outState.putParcelableArrayList(EXTRA_GUDANG, adapterGudang.mGudang)
            }
            Utils.TENGKULAK -> {
                outState.putParcelableArrayList(EXTRA_TENGKULAK, adapterTengkulak.mTengkulak)
            }
            Utils.PABRIK_PENGOLAHAN -> {
                outState.putParcelableArrayList(EXTRA_PABRIK_PENGOLAHAN, adapterPabrikPengolahan.mPabrikPengolahan)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAdapterProdukClicked) {
            adapterProduk.mProduk = ArrayList()
            loadData(nameList)
        }
        if (isAdapterProdusenClicked) {
            adapterProdusen.mProdusen = ArrayList()
            loadData(nameList)
        }
        if (isAdapterDistributorClicked) {
            adapterDistributor.mDistributor = ArrayList()
            loadData(nameList)
        }
        if (isAdapterPenerimaClicked) {
            adapterPenerima.mPenerima = ArrayList()
            loadData(nameList)
        }
        if (isAdapterPenggilingClicked) {
            adapterPenggiling.mPenggiling = ArrayList()
            loadData(nameList)
        }
        if (isAdapterPengepulClicked) {
            adapterPengepul.mPengepul = ArrayList()
            loadData(nameList)
        }
        if (isAdapterGudangClicked) {
            adapterGudang.mGudang = ArrayList()
            loadData(nameList)
        }
        if (isAdapterTengkulakClicked) {
            adapterTengkulak.mTengkulak = ArrayList()
            loadData(nameList)
        }
        if (isAdapterPabrikPengolahanClicked) {
            adapterPabrikPengolahan.mPabrikPengolahan = ArrayList()
            loadData(nameList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        traceableGoodHelper.close()
    }
}