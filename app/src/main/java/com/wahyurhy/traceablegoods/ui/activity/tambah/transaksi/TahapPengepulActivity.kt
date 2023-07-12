package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPengepulBinding
import com.wahyurhy.traceablegoods.utils.Utils

class TahapPengepulActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityTahapPengepulBinding

    private var selectedTahapSelanjutnya = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPengepulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()

        initClickListener()
    }

    private fun initClickListener() {
        binding.tahapSelanjutnyaSpinner.onItemSelectedListener = this

        binding.btnLanjut.setOnClickListener {
            when (selectedTahapSelanjutnya) {
                Utils.GUDANG -> startActivity(Intent(this, TahapGudangActivity::class.java))
                Utils.TENGKULAK -> startActivity(Intent(this, TahapTengkulakActivity::class.java))
                Utils.PENGGILING -> startActivity(Intent(this, TahapPenggilingActivity::class.java))
                Utils.PENGEPUL -> {
                    Toast.makeText(
                        this,
                        getString(R.string.tahap_sedang_dikerjakan, selectedTahapSelanjutnya),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Utils.PABRIK_PENGOLAHAN -> startActivity(Intent(this, TahapPabrikPengolahanActivity::class.java))
                Utils.PENERIMA -> startActivity(Intent(this, TahapPenerimaActivity::class.java))
            }
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..5) {
            when (binding.tahapSelanjutnyaSpinner.selectedItem) {
                resources.getStringArray(R.array.tahap_spinner)[i].toString() -> {
                    selectedTahapSelanjutnya = resources.getStringArray(R.array.tahap_spinner)[i].toString()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}