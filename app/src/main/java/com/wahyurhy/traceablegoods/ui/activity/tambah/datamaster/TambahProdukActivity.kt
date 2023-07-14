package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataProdukBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.PRODUK_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class TambahProdukActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private var selectedJenisProduk = ""

    private lateinit var binding: ActivityTambahDataProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initEditText()
        initClickListener()
    }

    private fun initEditText() {
        val produk = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUK) ?: ""
        binding.edtNamaProduk.setText(produk)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.jenisProdukSpinner.onItemSelectedListener = this

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val jenisProduk = selectedJenisProduk
                val namaProduk = edtNamaProduk.text.toString().trim()
                val merekProduk = edtMerekProduk.text.toString().trim()
                val noLot = edtNoLotProduk.text.toString().trim()
                val tanggalProduksi = edtTanggalProduksiProduk.text.toString().trim()
                val tanggalKadaluarsa = edtTanggalKadaluarsaProduk.text.toString().trim()
                val deskripsiProduk = edtDeskripsiProduk.text.toString().trim()

                namaProduk.showErrorIfEmpty(binding, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(PRODUK_ID, PRODUK, getCurrentDate() + " WIB")
                    val produkId = traceableGoodHelper.insertProduk(PRODUK_ID, jenisProduk,namaProduk, merekProduk, noLot, tanggalProduksi, tanggalKadaluarsa, deskripsiProduk, getCurrentDate() + " WIB")

                    if (produkId > 0) {
                        Toast.makeText(
                            this@TambahProdukActivity,
                            "Berhasil Tambah Data $PRODUK",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahProdukActivity,
                            "Gagal menambahkan data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun String.showErrorIfEmpty(binding: ActivityTambahDataProdukBinding, errorMessage: String) {
        if (this.isEmpty()) {
            isAllSet = false
            binding.edtNamaProduk.error = errorMessage
        } else {
            isAllSet = true
            binding.edtNamaProduk.error = null
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