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
import com.wahyurhy.traceablegoods.databinding.ActivityTahapProdusenBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TahapProdusenActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private lateinit var adapterProdusen: ArrayAdapter<String>
    private lateinit var adapterProduk: ArrayAdapter<String>
    private lateinit var adapterDistributor: ArrayAdapter<String>
    private var produsenList = ArrayList<String>()
    private var produkList = ArrayList<String>()
    private var distributorList = ArrayList<String>()
    private var mapProdusen: MutableMap<String, String> = mutableMapOf()
    private var mapProduk: MutableMap<String, String> = mutableMapOf()

    private lateinit var binding: ActivityTahapProdusenBinding

    private var selectedTahapSelanjutnya = ""
    private var selectedSatuan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapProdusenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        loadDataProdusen()
        loadDataProduk()
        loadDataDistributor()

        fitStatusBar()

        initClickListener()
    }

    private fun loadDataProdusen() {
        lifecycleScope.launch {
            binding.apply {
                val deferredProdusen = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllProdusen()
                    MappingHelper.mapCursorToArrayListProdusen(cursor)
                }
                val produsen = deferredProdusen.await()
                if (produsen.size > 0) {
                    produsen.forEach {
                        try {
                            produsenList.add("${it.namaProdusen} - ${it.noNpwp.substring(0, 3)}***${it.noNpwp.substring(it.noNpwp.length - 3)}")
                            mapProdusen["${it.namaProdusen} - ${it.noNpwp.substring(0, 3)}***${it.noNpwp.substring(it.noNpwp.length - 3)}"] = it.kategoriProdusen
                        } catch (e: Exception) {
                            produsenList.add(it.namaProdusen)
                            mapProdusen[it.namaProdusen] = it.kategoriProdusen
                            Log.e("TahapProdusenActivity", "Error: ${e.message}")
                        }
                    }
                    adapterProdusen = ArrayAdapter<String>(
                        this@TahapProdusenActivity,
                        android.R.layout.simple_list_item_1,
                        produsenList
                    )
                    edtNamaProdusen.setAdapter(adapterProdusen)
                } else {
                    produsenList = ArrayList()
                    Toast.makeText(
                        this@TahapProdusenActivity,
                        "Tidak ada data saat ini",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun loadDataProduk() {
        lifecycleScope.launch {
            binding.apply {
                val deferredProduk = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllProduk()
                    MappingHelper.mapCursorToArrayListProduk(cursor)
                }
                val produk = deferredProduk.await()
                if (produk.size > 0) {
                    produk.forEach {
                        produkList.add(it.namaProduk)
                        mapProduk[it.namaProduk] = it.jenisProduk
                    }
                    adapterProduk = ArrayAdapter<String>(
                        this@TahapProdusenActivity,
                        android.R.layout.simple_list_item_1,
                        produkList
                    )
                    edtNamaProduk.setAdapter(adapterProduk)
                } else {
                    produkList = ArrayList()
                    Toast.makeText(
                        this@TahapProdusenActivity,
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
                        this@TahapProdusenActivity,
                        android.R.layout.simple_list_item_1,
                        distributorList
                    )
                    edtNamaDistributor.setAdapter(adapterDistributor)
                } else {
                    distributorList = ArrayList()
                    Toast.makeText(
                        this@TahapProdusenActivity,
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
        binding.satuanSpinner.onItemSelectedListener = this

        binding.btnLanjut.setOnClickListener {
            when (selectedTahapSelanjutnya) {
                Utils.GUDANG -> startActivity(Intent(this, TahapGudangActivity::class.java))
                Utils.TENGKULAK -> startActivity(Intent(this, TahapTengkulakActivity::class.java))
                Utils.PENGGILING -> startActivity(Intent(this, TahapPenggilingActivity::class.java))
                Utils.PENGEPUL -> startActivity(Intent(this, TahapPengepulActivity::class.java))
                Utils.PABRIK_PENGOLAHAN -> startActivity(
                    Intent(
                        this,
                        TahapPabrikPengolahanActivity::class.java
                    )
                )
                Utils.PENERIMA -> startActivity(Intent(this, TahapPenerimaActivity::class.java))
            }
        }
        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val satuan = selectedSatuan
                val status = selectedTahapSelanjutnya
                val namaProdusen = edtNamaProdusen.text.toString()
                val namaProduk = edtNamaProduk.text.toString().trim()
                val tahap = mapProdusen[namaProdusen] ?: ""
                val jenisProduk = mapProduk[namaProduk] ?: ""
                val namaDistributor = edtNamaDistributor.text.toString().trim()
                val totalYangDiDistribusikan = edtTotalYangDidistribusikan.text.toString().trim()
                val lokasiAsal = edtLokasiAsal.text.toString().trim()
                val lokasiTujuan = edtLokasiTujuan.text.toString().trim()
                var batchId = ""
                try {
                    batchId = namaProdusen.substring(0, 2) + generateDateBatchId() + namaProdusen.length + lokasiAsal.substring(0, 3).uppercase()
                } catch (e: Exception) {
                    batchId = namaProdusen + generateDateBatchId() + namaProdusen.length + lokasiAsal.uppercase()
                    Log.e("TahapProdusenActivity", "Error: ${e.message}")
                }
                val produkBatch = "$namaProduk - $batchId"

                namaProdusen.showErrorIfEmpty(
                    binding.edtNamaProdusen,
                    getString(R.string.tidak_boleh_kosong)
                )
                namaProduk.showErrorIfEmpty(
                    binding.edtNamaProduk,
                    getString(R.string.tidak_boleh_kosong)
                )
                namaDistributor.showErrorIfEmpty(
                    binding.edtNamaDistributor,
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
                    val resultTransaksi = traceableGoodHelper.insertTransaksi(
                        batchId,
                        status,
                        jenisProduk,
                        namaProduk,
                        produkBatch,
                        selectedTahapSelanjutnya,
                        getCurrentDate() + " WIB"
                    )
                    val resultAlurTransaksi = traceableGoodHelper.insertAlurDistribusi(
                        batchId,
                        tahap,
                        status,
                        namaProdusen,
                        namaProduk,
                        produkBatch,
                        jenisProduk,
                        "",
                        "",
                        namaDistributor,
                        "$totalYangDiDistribusikan $satuan",
                        lokasiAsal,
                        lokasiTujuan,
                        getCurrentDate() + " WIB"
                    )

                    if (resultTransaksi > 0) {
                        if (resultAlurTransaksi > 0) {
                            Toast.makeText(
                                this@TahapProdusenActivity,
                                getString(R.string.berhasil_menambah_data, Utils.PRODUSEN),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@TahapProdusenActivity,
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

    private fun generateDateBatchId(): String {
        val dateFormat = SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault())
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
                    selectedTahapSelanjutnya =
                        resources.getStringArray(R.array.tahap_spinner)[i].toString()
                }
            }
        }
        for (i in 0..10) {
            when (binding.satuanSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuan =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}