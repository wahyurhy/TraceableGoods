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
import com.wahyurhy.traceablegoods.model.penerima.PenerimaModel
import com.wahyurhy.traceablegoods.model.penggiling.PenggilingModel
import com.wahyurhy.traceablegoods.model.produk.ProdukModel
import com.wahyurhy.traceablegoods.model.produsen.ProdusenModel
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
            "produk" -> rawListProduk()
            "produsen" -> rawListProdusen()
            "distributor" -> rawListDistributor()
            "penerima" -> rawListPenerima()
            "penggiling" -> rawListPenggiling()
        }
    }

    private fun rawListProduk() {
        val gson = Gson()
        val i = assets.open("produk.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, ProdukModel::class.java)

        bindData(dataList)
    }

    private fun rawListProdusen() {
        val gson = Gson()
        val i = assets.open("produsen.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, ProdusenModel::class.java)

        bindData(dataList)
    }

    private fun rawListDistributor() {
        val gson = Gson()
        val i = assets.open("distributor.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, DistributorModel::class.java)

        bindData(dataList)
    }

    private fun rawListPenerima() {
        val gson = Gson()
        val i = assets.open("penerima.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PenerimaModel::class.java)

        bindData(dataList)
    }

    private fun rawListPenggiling() {
        val gson = Gson()
        val i = assets.open("penggiling.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList = gson.fromJson(br, PenggilingModel::class.java)

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
}