package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataProdukBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.datainfo.Data
import com.wahyurhy.traceablegoods.model.datainfo.Item
import com.wahyurhy.traceablegoods.utils.Utils

class TambahProdukActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var itemDataInfo: Item? = null
    private var dataInfo: Data? = null
    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var selectedJenisProduk = ""

    private lateinit var binding: ActivityTambahDataProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
        initClickListener()
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.jenisProdukSpinner.onItemSelectedListener = this

        binding.btnSimpan.setOnClickListener {

        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..3) {
            when (binding.jenisProdukSpinner.selectedItem) {
                resources.getStringArray(R.array.jenis_produk_spinner)[i].toString() -> {
                    selectedJenisProduk = resources.getStringArray(R.array.jenis_produk_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedJenisProduk", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}