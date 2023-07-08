package com.wahyurhy.traceablegoods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.adapter.*
import com.wahyurhy.traceablegoods.databinding.ActivityListBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.detail.*
import com.wahyurhy.traceablegoods.ui.fragment.MasterDataFragment.Companion.NAME_LIST
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DESKRIPSI_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_GUDANG
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_MEREK_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NO_LOT_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGEPUL
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TENGKULAK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TGL_KADALUARSA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TGL_PRODUKSI_PRODUK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {

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

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nameList = intent.getStringExtra(NAME_LIST) ?: ""

        setAdapter(nameList)

        fitStatusBar()
        initExtras(nameList)
        initClickListener()

        if (savedInstanceState == null) {
            // proses ambil data
            loadData(nameList)
        } else {
            when (nameList) {
                Utils.PRODUK -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.produk.Item>(EXTRA_PRODUK)
                    if (list != null) {
                        adapterProduk.mProduk = list
                    }
                }
                Utils.PRODUSEN -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.produsen.Item>(EXTRA_PRODUSEN)
                    if (list != null) {
                        adapterProdusen.mProdusen = list
                    }
                }
                Utils.DISTRIBUTOR -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.distributor.Item>(EXTRA_DISTRIBUTOR)
                    if (list != null) {
                        adapterDistributor.mDistributor = list
                    }
                }
                Utils.PENERIMA -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.penerima.Item>(EXTRA_PENERIMA)
                    if (list != null) {
                        adapterPenerima.mPenerima = list
                    }
                }
                Utils.PENGGILING -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.penggiling.Item>(EXTRA_PENGGILING)
                    if (list != null) {
                        adapterPenggiling.mPenggiling = list
                    }
                }
                Utils.PENGEPUL -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.pengepul.Item>(EXTRA_PENGEPUL)
                    if (list != null) {
                        adapterPengepul.mPengepul = list
                    }
                }
                Utils.GUDANG -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.gudang.Item>(EXTRA_GUDANG)
                    if (list != null) {
                        adapterGudang.mGudang = list
                    }
                }
                Utils.TENGKULAK -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.tengkulak.Item>(EXTRA_TENGKULAK)
                    if (list != null) {
                        adapterTengkulak.mTengkulak = list
                    }
                }
                Utils.PABRIK_PENGOLAHAN -> {
                    val list = savedInstanceState.getParcelableArrayList<com.wahyurhy.traceablegoods.model.pabrikpengolahan.Item>(EXTRA_PABRIK_PENGOLAHAN)
                    if (list != null) {
                        adapterPabrikPengolahan.mPabrikPengolahan = list
                    }
                }
            }
        }
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
                val traceableGoodHelper = TraceableGoodHelper.getInstance(this@ListActivity)
                traceableGoodHelper.open()

                when (nameList) {
                    Utils.PRODUK -> {
                        val deferredProduk = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllProduk()
                            MappingHelper.mapCursorToArrayListProduk(cursor)
                        }
                        val produk = deferredProduk.await()
                        if (produk.size > 0) {
                            adapterProduk.mProduk = produk
                            adapterProduk.setOnClickedListener(object : ProdukAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailProduk = Intent(this@ListActivity, DetailProdukActivity::class.java)
                                    intentDetailProduk.apply {
                                        putExtra(NAME_LIST, nameList)
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
                                }
                            })
                        } else {
                            adapterProduk.mProduk = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.PRODUSEN -> {
                        val deferredProdusen = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllProdusen()
                            MappingHelper.mapCursorToArrayListProdusen(cursor)
                        }
                        val produsen = deferredProdusen.await()
                        if (produsen.size > 0) {
                            adapterProdusen.mProdusen = produsen
                            adapterProdusen.setOnClickedListener(object : ProdusenAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailProdusen = Intent(this@ListActivity, DetailProdusenActivity::class.java)
                                    intentDetailProdusen.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailProdusen)
                                }
                            })
                        } else {
                            adapterProdusen.mProdusen = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.DISTRIBUTOR -> {
                        val deferredDistributor = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllDistributor()
                            MappingHelper.mapCursorToArrayListDistributor(cursor)
                        }
                        val distributor = deferredDistributor.await()
                        if (distributor.size > 0) {
                            adapterDistributor.mDistributor = distributor
                            adapterDistributor.setOnClickedListener(object : DistributorAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailDistributor = Intent(this@ListActivity, DetailDistributorActivity::class.java)
                                    intentDetailDistributor.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailDistributor)
                                }
                            })
                        } else {
                            adapterDistributor.mDistributor = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.PENERIMA -> {
                        val deferredPenerima = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllPenerima()
                            MappingHelper.mapCursorToArrayListPenerima(cursor)
                        }
                        val penerima = deferredPenerima.await()
                        if (penerima.size > 0) {
                            adapterPenerima.mPenerima = penerima
                            adapterPenerima.setOnClickedListener(object : PenerimaAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailPenerima = Intent(this@ListActivity, DetailPenerimaActivity::class.java)
                                    intentDetailPenerima.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailPenerima)
                                }
                            })
                        } else {
                            adapterPenerima.mPenerima = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.PENGGILING -> {
                        val deferredPenggiling = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllPenggiling()
                            MappingHelper.mapCursorToArrayListPenggiling(cursor)
                        }
                        val penggiling = deferredPenggiling.await()
                        if (penggiling.size > 0) {
                            adapterPenggiling.mPenggiling = penggiling
                            adapterPenggiling.setOnClickedListener(object : PenggilingAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailPenggiling = Intent(this@ListActivity, DetailPenggilingActivity::class.java)
                                    intentDetailPenggiling.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailPenggiling)
                                }
                            })
                        } else {
                            adapterPenggiling.mPenggiling = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.PENGEPUL -> {
                        val deferredPengepul = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllPengepul()
                            MappingHelper.mapCursorToArrayListPengepul(cursor)
                        }
                        val pengepul = deferredPengepul.await()
                        if (pengepul.size > 0) {
                            adapterPengepul.mPengepul = pengepul
                            adapterPengepul.setOnClickedListener(object : PengepulAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailPengepul = Intent(this@ListActivity, DetailPengepulActivity::class.java)
                                    intentDetailPengepul.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailPengepul)
                                }
                            })
                        } else {
                            adapterPengepul.mPengepul = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.GUDANG -> {
                        val deferredGudang = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllGudang()
                            MappingHelper.mapCursorToArrayListGudang(cursor)
                        }
                        val gudang = deferredGudang.await()
                        if (gudang.size > 0) {
                            adapterGudang.mGudang = gudang
                            adapterGudang.setOnClickedListener(object : GudangAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailGudang = Intent(this@ListActivity, DetailGudangActivity::class.java)
                                    intentDetailGudang.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailGudang)
                                }
                            })
                        } else {
                            adapterGudang.mGudang = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.TENGKULAK -> {
                        val deferredTengkulak = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllTengkulak()
                            MappingHelper.mapCursorToArrayListTengkulak(cursor)
                        }
                        val tengkulak = deferredTengkulak.await()
                        if (tengkulak.size > 0) {
                            adapterTengkulak.mTengkulak = tengkulak
                            adapterTengkulak.setOnClickedListener(object : TengkulakAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailTengkulak = Intent(this@ListActivity, DetailTengkulakActivity::class.java)
                                    intentDetailTengkulak.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailTengkulak)
                                }
                            })
                        } else {
                            adapterTengkulak.mTengkulak = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                    Utils.PABRIK_PENGOLAHAN -> {
                        val deferredPabrikPengolahan = async(Dispatchers.IO) {
                            val cursor = traceableGoodHelper.queryAllPabrikPengolahan()
                            MappingHelper.mapCursorToArrayListPabrikPengolahan(cursor)
                        }
                        val pabrikPengolahan = deferredPabrikPengolahan.await()
                        if (pabrikPengolahan.size > 0) {
                            adapterPabrikPengolahan.mPabrikPengolahan = pabrikPengolahan
                            adapterPabrikPengolahan.setOnClickedListener(object : PabrikPengolahanAdapter.OnItemClickListener {
                                override fun onItemClick(itemView: View?, position: Int) {
                                    val intentDetailPabrikPengolahan = Intent(this@ListActivity, DetailPabrikPengolahanActivity::class.java)
                                    intentDetailPabrikPengolahan.putExtra(NAME_LIST, nameList)
                                    startActivity(intentDetailPabrikPengolahan)
                                }
                            })
                        } else {
                            adapterPabrikPengolahan.mPabrikPengolahan = ArrayList()
                            Toast.makeText(this@ListActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                                .show()
                        }
                        traceableGoodHelper.close()
                    }
                }
            }
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
}