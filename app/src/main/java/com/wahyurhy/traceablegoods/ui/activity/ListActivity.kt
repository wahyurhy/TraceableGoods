package com.wahyurhy.traceablegoods.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.adapter.*
import com.wahyurhy.traceablegoods.databinding.ActivityListBinding
import com.wahyurhy.traceablegoods.model.distributor.DistributorModel
import com.wahyurhy.traceablegoods.model.gudang.GudangModel
import com.wahyurhy.traceablegoods.model.pabrikpengolahan.PabrikPengolahanModel
import com.wahyurhy.traceablegoods.model.penerima.PenerimaModel
import com.wahyurhy.traceablegoods.model.pengepul.PengepulModel
import com.wahyurhy.traceablegoods.model.penggiling.PenggilingModel
import com.wahyurhy.traceablegoods.model.produk.ProdukModel
import com.wahyurhy.traceablegoods.model.produsen.ProdusenModel
import com.wahyurhy.traceablegoods.model.tengkulak.TengkulakModel
import com.wahyurhy.traceablegoods.ui.activity.detail.*
import com.wahyurhy.traceablegoods.ui.fragment.MasterDataFragment.Companion.NAME_LIST
import com.wahyurhy.traceablegoods.utils.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameList = intent.getStringExtra(NAME_LIST) ?: ""

        fitStatusBar()
        initExtras(nameList)
        initClickListener()

        initRawList(nameList)
    }

    private fun initRawList(nameList: String) {
        when (nameList.lowercase()) {
            PRODUK -> rawListProduk(nameList)
            PRODUSEN -> rawListProdusen(nameList)
            DISTRIBUTOR -> rawListDistributor(nameList)
            PENERIMA -> rawListPenerima(nameList)
            PENGGILING -> rawListPenggiling(nameList)
            PENGEPUL -> rawListPengepul(nameList)
            GUDANG -> rawListGudang(nameList)
            TENGKULAK -> rawListTengkulak(nameList)
            PABRIK_PENGOLAHAN -> rawListPabrikPengolahan(nameList)
        }
    }

    private fun rawListProduk(nameList: String) {
        val gson = Gson()
        val i = assets.open(PRODUK_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, ProdukModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListProdusen(nameList: String) {
        val gson = Gson()
        val i = assets.open(PRODUSEN_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, ProdusenModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListDistributor(nameList: String) {
        val gson = Gson()
        val i = assets.open(DISTRIBUTOR_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, DistributorModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListPenerima(nameList: String) {
        val gson = Gson()
        val i = assets.open(PENERIMA_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PenerimaModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListPenggiling(nameList: String) {
        val gson = Gson()
        val i = assets.open(PENGGILINNG_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PenggilingModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListPengepul(nameList: String) {
        val gson = Gson()
        val i = assets.open(PENGEPUL_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PengepulModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListGudang(nameList: String) {
        val gson = Gson()
        val i = assets.open(GUDANG_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, GudangModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListTengkulak(nameList: String) {
        val gson = Gson()
        val i = assets.open(TENGKULAK_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, TengkulakModel::class.java)

        bindData(dataList, nameList)
    }

    private fun rawListPabrikPengolahan(nameList: String) {
        val gson = Gson()
        val i = assets.open(PABRIK_PENGOLAHAN_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PabrikPengolahanModel::class.java)

        bindData(dataList, nameList)
    }

    private fun bindData(dataList: ProdukModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = ProdukAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : ProdukAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val produk = result.items[position].namaProduk
                    Toast.makeText(this@ListActivity, "$produk was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailProduk = Intent(this@ListActivity, DetailProdukActivity::class.java)
                    intentDetailProduk.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailProduk)
                }
            })
        }
    }

    private fun bindData(dataList: ProdusenModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = ProdusenAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : ProdusenAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val produsen = result.items[position].namaProdusen
                    Toast.makeText(this@ListActivity, "$produsen was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailProdusen = Intent(this@ListActivity, DetailProdusenActivity::class.java)
                    intentDetailProdusen.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailProdusen)
                }
            })
        }
    }

    private fun bindData(dataList: DistributorModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = DistributorAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : DistributorAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val distributor = result.items[position].namaDistributor
                    Toast.makeText(this@ListActivity, "$distributor was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailDistributor = Intent(this@ListActivity, DetailDistributorActivity::class.java)
                    intentDetailDistributor.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailDistributor)
                }
            })
        }
    }

    private fun bindData(dataList: PenggilingModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = PenggilingAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PenggilingAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val penggiling = result.items[position].namaPenggiling
                    Toast.makeText(this@ListActivity, "$penggiling was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailPenggiling = Intent(this@ListActivity, DetailPenggilingActivity::class.java)
                    intentDetailPenggiling.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailPenggiling)
                }
            })
        }
    }

    private fun bindData(dataList: PenerimaModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = PenerimaAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PenerimaAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val penerima = result.items[position].namaPenerima
                    Toast.makeText(this@ListActivity, "$penerima was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailPenerima = Intent(this@ListActivity, DetailPenerimaActivity::class.java)
                    intentDetailPenerima.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailPenerima)
                }
            })
        }
    }

    private fun bindData(dataList: PengepulModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = PengepulAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PengepulAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val pengepul = result.items[position].namaPengepul
                    Toast.makeText(this@ListActivity, "$pengepul was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailPengepul = Intent(this@ListActivity, DetailPengepulActivity::class.java)
                    intentDetailPengepul.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailPengepul)
                }
            })
        }
    }

    private fun bindData(dataList: GudangModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = GudangAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : GudangAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val gudang = result.items[position].namaGudang
                    Toast.makeText(this@ListActivity, "$gudang was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailGudang = Intent(this@ListActivity, DetailGudangActivity::class.java)
                    intentDetailGudang.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailGudang)
                }
            })
        }
    }

    private fun bindData(dataList: TengkulakModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = TengkulakAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : TengkulakAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val gudang = result.items[position].namaTengkulak
                    Toast.makeText(this@ListActivity, "$gudang was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailTengkulak = Intent(this@ListActivity, DetailTengkulakActivity::class.java)
                    intentDetailTengkulak.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailTengkulak)
                }
            })
        }
    }

    private fun bindData(dataList: PabrikPengolahanModel, nameList: String) {
        dataList.result.forEach { result ->
            val adapter = PabrikPengolahanAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PabrikPengolahanAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val gudang = result.items[position].namaPabrikPengolahan
                    Toast.makeText(this@ListActivity, "$gudang was clicked!", Toast.LENGTH_SHORT).show()
                    val intentDetailPabrikPengolahan = Intent(this@ListActivity, DetailPabrikPengolahanActivity::class.java)
                    intentDetailPabrikPengolahan.putExtra(NAME_LIST, nameList)
                    startActivity(intentDetailPabrikPengolahan)
                }
            })
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

    companion object {
        const val PRODUK = "produk"
        const val PRODUSEN = "produsen"
        const val DISTRIBUTOR = "distributor"
        const val PENERIMA = "penerima"
        const val PENGGILING = "penggiling"
        const val PENGEPUL = "pengepul"
        const val GUDANG = "gudang"
        const val TENGKULAK = "tengkulak"
        const val PABRIK_PENGOLAHAN = "pabrik pengolahan"

        const val PRODUK_JSON = "produk.json"
        const val PRODUSEN_JSON = "produsen.json"
        const val DISTRIBUTOR_JSON = "distributor.json"
        const val PENERIMA_JSON = "penerima.json"
        const val PENGGILINNG_JSON = "penggiling.json"
        const val PENGEPUL_JSON = "pengepul.json"
        const val GUDANG_JSON = "gudang.json"
        const val TENGKULAK_JSON = "tengkulak.json"
        const val PABRIK_PENGOLAHAN_JSON = "pabrik-pengolahan.json"
    }
}