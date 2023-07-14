package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPenerimaBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.PENERIMA_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class TambahPenerimaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private var selectedKategoriPenerima = ""

    private lateinit var binding: ActivityTambahDataPenerimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPenerimaBinding.inflate(layoutInflater)
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

        binding.kategoriPenerimaSpinner.onItemSelectedListener = this

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val kategoriPenerima = selectedKategoriPenerima
                val namaPenerima = edtNamaPenerima.text.toString().trim()
                val kontakPenerima = edtKontakPenerima.text.toString().trim()
                val alamatPenerima = edtAlamatPenerima.text.toString().trim()

                namaPenerima.showErrorIfEmpty(binding.edtNamaPenerima, getString(R.string.tidak_boleh_kosong))
                kontakPenerima.showErrorIfEmpty(binding.edtKontakPenerima, getString(R.string.tidak_boleh_kosong))
                alamatPenerima.showErrorIfEmpty(binding.edtAlamatPenerima, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(PENERIMA_ID, PENERIMA, getCurrentDate() + " WIB")
                    val penerimaId = traceableGoodHelper.insertPenerima(PENERIMA_ID, namaPenerima, kategoriPenerima, alamatPenerima, kontakPenerima, getCurrentDate() + " WIB")

                    if (penerimaId > 0) {
                        Toast.makeText(
                            this@TambahPenerimaActivity,
                            "Berhasil Tambah Data $PENERIMA",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahPenerimaActivity,
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..7) {
            when (binding.kategoriPenerimaSpinner.selectedItem) {
                resources.getStringArray(R.array.kategori_penerima_spinner)[i].toString() -> {
                    selectedKategoriPenerima = resources.getStringArray(R.array.kategori_penerima_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedKategoriPenerima", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}