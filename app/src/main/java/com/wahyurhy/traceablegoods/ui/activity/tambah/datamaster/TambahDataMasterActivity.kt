package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataMasterBinding
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.GUDANG
import com.wahyurhy.traceablegoods.utils.Utils.PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.PENGEPUL
import com.wahyurhy.traceablegoods.utils.Utils.PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.PRODUSEN
import com.wahyurhy.traceablegoods.utils.Utils.TENGKULAK

class TambahDataMasterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityTambahDataMasterBinding

    private var selectedJenisData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataMasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
        initClickListener()
    }

    private fun initClickListener() {
        binding.jenisDataSpinner.onItemSelectedListener = this

        binding.btnLanjut.setOnClickListener {
            when (selectedJenisData.lowercase()) {
                PRODUK -> startActivity(Intent(this, TambahProdukActivity::class.java))
                PRODUSEN -> startActivity(Intent(this, TambahProdusenActivity::class.java))
                DISTRIBUTOR -> startActivity(Intent(this, TambahDistributorActivity::class.java))
                PENERIMA -> startActivity(Intent(this, TambahPenerimaActivity::class.java))
                PENGGILING -> startActivity(Intent(this, TambahPenggilingActivity::class.java))
                PENGEPUL -> startActivity(Intent(this, TambahPengepulActivity::class.java))
                GUDANG -> startActivity(Intent(this, TambahGudangActivity::class.java))
                TENGKULAK -> startActivity(Intent(this, TambahTengkulakActivity::class.java))
                PABRIK_PENGOLAHAN -> startActivity(Intent(this, TambahPabrikPengolahanActivity::class.java))
            }
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..8) {
            when (binding.jenisDataSpinner.selectedItem) {
                resources.getStringArray(R.array.jenis_data_spinner)[i].toString() -> {
                    selectedJenisData = resources.getStringArray(R.array.jenis_data_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedJenisData", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}