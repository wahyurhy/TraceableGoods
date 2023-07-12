package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPabrikPengolahanBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TahapPabrikPengolahanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private lateinit var adapterPabrikPengolahan: ArrayAdapter<String>
    private lateinit var adapterDistributor: ArrayAdapter<String>
    private var pabrikPengolahanList = ArrayList<String>()
    private var distributorList = ArrayList<String>()

    private lateinit var binding: ActivityTahapPabrikPengolahanBinding

    private var selectedTahapSelanjutnya = ""
    private var selectedSatuanYangDiterima = ""
    private var selectedSatuanYangDiDistribusikan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPabrikPengolahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        loadDataPabrikPengolahan()
        loadDataDistributor()

        fitStatusBar()

        initClickListener()
    }

    private fun loadDataPabrikPengolahan() {
        lifecycleScope.launch {
            binding.apply {
                val deferredPabrikPengolahan = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllPabrikPengolahan()
                    MappingHelper.mapCursorToArrayListPabrikPengolahan(cursor)
                }
                val pabrikPengolahan = deferredPabrikPengolahan.await()
                if (pabrikPengolahan.size > 0) {
                    pabrikPengolahan.forEach {
                        try {
                            pabrikPengolahanList.add("${it.namaPabrikPengolahan} - ${it.kontakPabrikPengolahan.substring(0, 3)}***${it.kontakPabrikPengolahan.substring(it.kontakPabrikPengolahan.length - 3)}")
                        } catch (e: Exception) {
                            pabrikPengolahanList.add(it.namaPabrikPengolahan)
                            Log.e("TahapPabrikActivity", "Error: ${e.message}")
                        }
                    }
                    adapterPabrikPengolahan = ArrayAdapter<String>(
                        this@TahapPabrikPengolahanActivity,
                        android.R.layout.simple_list_item_1,
                        pabrikPengolahanList
                    )
                    edtNamaPabrikPengolahan.setAdapter(adapterPabrikPengolahan)
                } else {
                    pabrikPengolahanList = ArrayList()
                    Toast.makeText(
                        this@TahapPabrikPengolahanActivity,
                        "Tidak ada data saat ini",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun loadDataDistributor() {
        lifecycleScope.launch {
            binding.apply {
                val deferredDistributor = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllDistributor()
                    MappingHelper.mapCursorToArrayListDistributor(cursor)
                }
                val distributor = deferredDistributor.await()
                if (distributor.size > 0) {
                    distributor.forEach {
                        distributorList.add(it.namaDistributor)
                    }
                    adapterDistributor = ArrayAdapter<String>(
                        this@TahapPabrikPengolahanActivity,
                        android.R.layout.simple_list_item_1,
                        distributorList
                    )
                    edtNamaDistributorSelanjutnya.setAdapter(adapterDistributor)
                } else {
                    distributorList = ArrayList()
                    Toast.makeText(
                        this@TahapPabrikPengolahanActivity,
                        "Tidak ada data saat ini",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tahapSelanjutnyaSpinner.onItemSelectedListener = this
        binding.satuanDiterimaSpinner.onItemSelectedListener = this
        binding.satuanDiDistibusikanSpinner.onItemSelectedListener = this

        binding.btnLanjut.setOnClickListener {
            when (selectedTahapSelanjutnya) {
                Utils.GUDANG -> startActivity(Intent(this, TahapGudangActivity::class.java))
                Utils.TENGKULAK -> startActivity(Intent(this, TahapTengkulakActivity::class.java))
                Utils.PENGGILING -> startActivity(Intent(this, TahapPenggilingActivity::class.java))
                Utils.PENGEPUL -> startActivity(Intent(this, TahapPengepulActivity::class.java))
                Utils.PABRIK_PENGOLAHAN -> {
                    Toast.makeText(
                        this,
                        getString(R.string.tahap_sedang_dikerjakan, selectedTahapSelanjutnya),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Utils.PENERIMA -> startActivity(Intent(this, TahapPenerimaActivity::class.java))
            }
        }
        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val satuanYangDiterima = selectedSatuanYangDiterima
                val satuanYangDiDistribusikan = selectedSatuanYangDiDistribusikan
                val status = selectedTahapSelanjutnya
                val namaPabrikPengolahan = edtNamaPabrikPengolahan.text.toString()
                val tahap = getString(R.string.pabrik_pengolahan)
                val namaDistributorSelanjutnya =
                    edtNamaDistributorSelanjutnya.text.toString().trim()
                val totalYangDiterima = edtTotalYangDiterima.text.toString().trim()
                val totalYangDiDistribusikan = edtTotalYangDidistribusikan.text.toString().trim()
                val lokasiAsal = edtLokasiAsal.text.toString().trim()
                val lokasiTujuan = edtLokasiTujuan.text.toString().trim()

                val transaksiIdExtra = intent.getIntExtra(Utils.EXTRA_TRANSAKSI_ID, 0)
                val batchIdExtra = intent.getStringExtra(Utils.EXTRA_BATCH_ID) ?: ""
                val jenisProdukExtra = intent.getStringExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI) ?: ""
                val namaProdukExtra = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI) ?: ""
                val produkBatchExtra = intent.getStringExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI) ?: ""

                namaPabrikPengolahan.showErrorIfEmpty(
                    binding.edtNamaPabrikPengolahan,
                    getString(R.string.tidak_boleh_kosong)
                )
                namaDistributorSelanjutnya.showErrorIfEmpty(
                    binding.edtNamaDistributorSelanjutnya,
                    getString(R.string.tidak_boleh_kosong)
                )
                totalYangDiterima.showErrorIfEmpty(
                    binding.edtTotalYangDiterima,
                    getString(R.string.tidak_boleh_kosong)
                )
                totalYangDiDistribusikan.showErrorIfEmpty(
                    binding.edtTotalYangDidistribusikan,
                    getString(R.string.tidak_boleh_kosong)
                )
                lokasiAsal.showErrorIfEmpty(
                    binding.edtLokasiAsal,
                    getString(R.string.tidak_boleh_kosong)
                )
                lokasiTujuan.showErrorIfEmpty(
                    binding.edtLokasiTujuan,
                    getString(R.string.tidak_boleh_kosong)
                )

                if (isAllSet) {
                    val resultTransaksi = traceableGoodHelper.updateTransaksi(
                        transaksiIdExtra.toString(),
                        batchIdExtra,
                        status,
                        jenisProdukExtra,
                        namaProdukExtra,
                        produkBatchExtra,
                        selectedTahapSelanjutnya,
                        getCurrentDate() + " WIB"
                    )
                    val resultAlurTransaksi = traceableGoodHelper.insertAlurDistribusi(
                        batchIdExtra,
                        tahap,
                        status,
                        namaPabrikPengolahan,
                        "",
                        "",
                        "",
                        "$totalYangDiterima $satuanYangDiterima",
                        "",
                        namaDistributorSelanjutnya,
                        "$totalYangDiDistribusikan $satuanYangDiDistribusikan",
                        lokasiAsal,
                        lokasiTujuan,
                        getCurrentDate() + " WIB"
                    )

                    if (resultTransaksi > 0) {
                        if (resultAlurTransaksi > 0) {
                            Toast.makeText(
                                this@TahapPabrikPengolahanActivity,
                                getString(R.string.berhasil_menambah_data, Utils.PABRIK_PENGOLAHAN),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@TahapPabrikPengolahanActivity,
                            getString(R.string.gagal_menambah_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy - HH:mm", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    private fun String.showErrorIfEmpty(binding: AutoCompleteTextView, errorMessage: String) {
        if (this.isEmpty()) {
            isAllSet = false
            binding.error = errorMessage
        } else {
            isAllSet = true
            binding.error = null
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
        for (i in 0..5) {
            when (binding.tahapSelanjutnyaSpinner.selectedItem) {
                resources.getStringArray(R.array.tahap_spinner)[i].toString() -> {
                    selectedTahapSelanjutnya = resources.getStringArray(R.array.tahap_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedTahapSelanjutnya", Toast.LENGTH_SHORT).show()
                }
            }
        }
        for (i in 0..10) {
            when (binding.satuanDiterimaSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuanYangDiterima =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString()
                    Toast.makeText(
                        this,
                        "diterima satuan: $selectedSatuanYangDiterima",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            when (binding.satuanDiDistibusikanSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuanYangDiDistribusikan =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString()
                    Toast.makeText(
                        this,
                        "didistribusikan satuan: $selectedSatuanYangDiDistribusikan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}