package com.wahyurhy.traceablegoods.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.adapter.*
import com.wahyurhy.traceablegoods.databinding.ActivityListProductBinding
import com.wahyurhy.traceablegoods.model.distributor.DistributorModel
import com.wahyurhy.traceablegoods.model.gudang.GudangModel
import com.wahyurhy.traceablegoods.model.pabrikpengolahan.PabrikPengolahanModel
import com.wahyurhy.traceablegoods.model.penerima.PenerimaModel
import com.wahyurhy.traceablegoods.model.pengepul.PengepulModel
import com.wahyurhy.traceablegoods.model.penggiling.PenggilingModel
import com.wahyurhy.traceablegoods.model.produk.ProdukModel
import com.wahyurhy.traceablegoods.model.produsen.ProdusenModel
import com.wahyurhy.traceablegoods.model.tengkulak.TengkulakModel
import com.wahyurhy.traceablegoods.ui.fragment.MasterDataFragment.Companion.NAME_LIST
import com.wahyurhy.traceablegoods.utils.Utils
import java.io.BufferedReader
import java.io.InputStreamReader

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
        initExtras()
        initClickListener()

        val nameList = intent.getStringExtra(NAME_LIST) ?: ""
        when (nameList.lowercase()) {
            PRODUK -> rawListProduk()
            PRODUSEN -> rawListProdusen()
            DISTRIBUTOR -> rawListDistributor()
            PENERIMA -> rawListPenerima()
            PENGGILING -> rawListPenggiling()
            PENGEPUL -> rawListPengepul()
            GUDANG -> rawListGudang()
            TENGKULAK -> rawListTengkulak()
            PABRIK_PENGOLAHAN -> rawListPabrikPengolahan()
        }
    }

    private fun rawListProduk() {
        val gson = Gson()
        val i = assets.open(PRODUK_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, ProdukModel::class.java)

        bindData(dataList)
    }

    private fun rawListProdusen() {
        val gson = Gson()
        val i = assets.open(PRODUSEN_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, ProdusenModel::class.java)

        bindData(dataList)
    }

    private fun rawListDistributor() {
        val gson = Gson()
        val i = assets.open(DISTRIBUTOR_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, DistributorModel::class.java)

        bindData(dataList)
    }

    private fun rawListPenerima() {
        val gson = Gson()
        val i = assets.open(PENERIMA_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PenerimaModel::class.java)

        bindData(dataList)
    }

    private fun rawListPenggiling() {
        val gson = Gson()
        val i = assets.open(PENGGILINNG_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PenggilingModel::class.java)

        bindData(dataList)
    }

    private fun rawListPengepul() {
        val gson = Gson()
        val i = assets.open(PENGEPUL_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PengepulModel::class.java)

        bindData(dataList)
    }

    private fun rawListGudang() {
        val gson = Gson()
        val i = assets.open(GUDANG_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, GudangModel::class.java)

        bindData(dataList)
    }

    private fun rawListTengkulak() {
        val gson = Gson()
        val i = assets.open(TENGKULAK_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, TengkulakModel::class.java)

        bindData(dataList)
    }

    private fun rawListPabrikPengolahan() {
        val gson = Gson()
        val i = assets.open(PABRIK_PENGOLAHAN_JSON)
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PabrikPengolahanModel::class.java)

        bindData(dataList)
    }

    private fun bindData(dataList: ProdukModel) {
        dataList.result.forEach { result ->
            val adapter = ProdukAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : ProdukAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val produk = result.items[position].namaProduk
                    Toast.makeText(this@ListActivity, "$produk was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: ProdusenModel) {
        dataList.result.forEach { result ->
            val adapter = ProdusenAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : ProdusenAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val produsen = result.items[position].namaProdusen
                    Toast.makeText(this@ListActivity, "$produsen was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: DistributorModel) {
        dataList.result.forEach { result ->
            val adapter = DistributorAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : DistributorAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val distributor = result.items[position].namaDistributor
                    Toast.makeText(this@ListActivity, "$distributor was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: PenggilingModel) {
        dataList.result.forEach { result ->
            val adapter = PenggilingAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PenggilingAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val penggiling = result.items[position].namaPenggiling
                    Toast.makeText(this@ListActivity, "$penggiling was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: PenerimaModel) {
        dataList.result.forEach { result ->
            val adapter = PenerimaAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PenerimaAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val penerima = result.items[position].namaPenerima
                    Toast.makeText(this@ListActivity, "$penerima was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: PengepulModel) {
        dataList.result.forEach { result ->
            val adapter = PengepulAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PengepulAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val pengepul = result.items[position].namaPengepul
                    Toast.makeText(this@ListActivity, "$pengepul was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: GudangModel) {
        dataList.result.forEach { result ->
            val adapter = GudangAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : GudangAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val gudang = result.items[position].namaGudang
                    Toast.makeText(this@ListActivity, "$gudang was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: TengkulakModel) {
        dataList.result.forEach { result ->
            val adapter = TengkulakAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : TengkulakAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val gudang = result.items[position].namaTengkulak
                    Toast.makeText(this@ListActivity, "$gudang was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun bindData(dataList: PabrikPengolahanModel) {
        dataList.result.forEach { result ->
            val adapter = PabrikPengolahanAdapter(result.items)
            binding.rvList.adapter = adapter
            adapter.setOnClickedListener(object : PabrikPengolahanAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View?, position: Int) {
                    val gudang = result.items[position].namaPabrikPengolahan
                    Toast.makeText(this@ListActivity, "$gudang was clicked!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initExtras() {
        val nameList = intent.getStringExtra(NAME_LIST) ?: ""
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