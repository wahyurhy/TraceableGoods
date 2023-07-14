package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPabrikPengolahanBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.PABRIK_PENGOLAHAN
import com.wahyurhy.traceablegoods.utils.Utils.PABRIK_PENGOLAHAN_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class TambahPabrikPengolahanActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private lateinit var binding: ActivityTambahDataPabrikPengolahanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPabrikPengolahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initClickListener()
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val namaPabrikPengolahan = edtNamaPabrikPengolahan.text.toString().trim()
                val kontakPabrikPengolahan = edtKontakPabrikPengolahan.text.toString().trim()
                val alamatPabrikPengolahan = edtAlamatPabrikPengolahan.text.toString().trim()

                namaPabrikPengolahan.showErrorIfEmpty(binding.edtNamaPabrikPengolahan, getString(R.string.tidak_boleh_kosong))
                kontakPabrikPengolahan.showErrorIfEmpty(binding.edtKontakPabrikPengolahan, getString(R.string.tidak_boleh_kosong))
                alamatPabrikPengolahan.showErrorIfEmpty(binding.edtAlamatPabrikPengolahan, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(PABRIK_PENGOLAHAN_ID, PABRIK_PENGOLAHAN, getCurrentDate() + " WIB")
                    val distributorId = traceableGoodHelper.insertPabrikPengolahan(PABRIK_PENGOLAHAN_ID, namaPabrikPengolahan, alamatPabrikPengolahan, kontakPabrikPengolahan, getCurrentDate() + " WIB")

                    if (distributorId > 0) {
                        Toast.makeText(
                            this@TambahPabrikPengolahanActivity,
                            "Berhasil Tambah Data $PABRIK_PENGOLAHAN",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahPabrikPengolahanActivity,
                            "Gagal menambahkan data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun String.showErrorIfEmpty(binding: AppCompatEditText, errorMessage: String) {
        if (this.isEmpty()) {
            isAllSet = false
            binding.error = errorMessage
        } else {
            isAllSet = true
            binding.error = null
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}