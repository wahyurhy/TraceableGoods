package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataTengkulakBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.TENGKULAK
import com.wahyurhy.traceablegoods.utils.Utils.TENGKULAK_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class TambahTengkulakActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private lateinit var binding: ActivityTambahDataTengkulakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataTengkulakBinding.inflate(layoutInflater)
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
                val namaTengkulak = edtNamaTengkulak.text.toString().trim()
                val kontakTengkulak = edtKontakTengkulak.text.toString().trim()
                val alamatTengkulak = edtAlamatTengkulak.text.toString().trim()

                namaTengkulak.showErrorIfEmpty(binding.edtNamaTengkulak, getString(R.string.tidak_boleh_kosong))
                kontakTengkulak.showErrorIfEmpty(binding.edtKontakTengkulak, getString(R.string.tidak_boleh_kosong))
                alamatTengkulak.showErrorIfEmpty(binding.edtAlamatTengkulak, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(TENGKULAK_ID, TENGKULAK, getCurrentDate() + " WIB")
                    val distributorId = traceableGoodHelper.insertTengkulak(TENGKULAK_ID, namaTengkulak, alamatTengkulak, kontakTengkulak, getCurrentDate() + " WIB")

                    if (distributorId > 0) {
                        Toast.makeText(
                            this@TambahTengkulakActivity,
                            "Berhasil Tambah Data $TENGKULAK",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahTengkulakActivity,
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