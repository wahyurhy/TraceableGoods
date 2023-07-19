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
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPengepulBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapgudang.TahapGudangActivity
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TahapPengepulActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private lateinit var adapterPengepul: ArrayAdapter<String>
    private lateinit var adapterDistributor: ArrayAdapter<String>
    private var pengepulList = ArrayList<String>()
    private var distributorList = ArrayList<String>()

    private lateinit var binding: ActivityTahapPengepulBinding

    private var selectedTahapSelanjutnya = ""
    private var selectedSatuanYangDiterima = ""
    private var selectedSatuanYangDiDistribusikan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPengepulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        loadDataPengepul()
        loadDataDistributor()

        fitStatusBar()

        initClickListener()
    }

    private fun loadDataPengepul() {
        lifecycleScope.launch {
            binding.apply {
                val deferredPengepul = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllPengepul()
                    MappingHelper.mapCursorToArrayListPengepul(cursor)
                }
                val pengepul = deferredPengepul.await()
                if (pengepul.size > 0) {
                    pengepul.forEach {
                        try {
                            pengepulList.add("${it.namaPengepul} - ${it.kontakPengepul.substring(0, 3)}***${it.kontakPengepul.substring(it.kontakPengepul.length - 3)}")
                        } catch (e: Exception) {
                            pengepulList.add(it.namaPengepul)
                            Log.e("TahapPengepulActivity", "Error: ${e.message}")
                        }
                    }
                    adapterPengepul = ArrayAdapter<String>(
                        this@TahapPengepulActivity,
                        android.R.layout.simple_list_item_1,
                        pengepulList
                    )
                    edtNamaPengepul.setAdapter(adapterPengepul)
                } else {
                    pengepulList = ArrayList()
                    Toast.makeText(
                        this@TahapPengepulActivity,
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
                        try {
                            distributorList.add(
                                "${it.namaDistributor} - ${
                                    it.kontakDistributor.substring(
                                        0,
                                        3
                                    )
                                }***${it.kontakDistributor.substring(it.kontakDistributor.length - 3)}"
                            )
                        } catch (e: Exception) {
                            distributorList.add(it.namaDistributor)
                            Log.e("TahapPengepulActivity", "Error: ${e.message}")
                        }
                    }
                    adapterDistributor = ArrayAdapter<String>(
                        this@TahapPengepulActivity,
                        android.R.layout.simple_list_item_1,
                        distributorList
                    )
                    edtNamaDistributorSelanjutnya.setAdapter(adapterDistributor)
                } else {
                    distributorList = ArrayList()
                    Toast.makeText(
                        this@TahapPengepulActivity,
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
                Utils.GUDANG -> {
                    simpanData(Utils.GUDANG)
                }
                Utils.TENGKULAK -> {
                    simpanData(Utils.TENGKULAK)
                }
                Utils.PENGGILING -> {
                    simpanData(Utils.PENGGILING)
                }
                Utils.PENGEPUL -> {
                    Toast.makeText(
                        this,
                        getString(R.string.tahap_sedang_dikerjakan, selectedTahapSelanjutnya),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Utils.PABRIK_PENGOLAHAN -> {
                    simpanData(Utils.PABRIK_PENGOLAHAN)
                }
                Utils.PENERIMA -> {
                    simpanData(Utils.PENERIMA)
                }
            }
        }
        binding.btnSimpan.setOnClickListener {
            simpanData(getString(R.string.simpan))
        }
    }

    private fun simpanData(tahapSelanjutnya: String) {
        binding.apply {
            val satuanYangDiterima = selectedSatuanYangDiterima
            val satuanYangDiDistribusikan = selectedSatuanYangDiDistribusikan
            val status = selectedTahapSelanjutnya
            val namaPengepul = edtNamaPengepul.text.toString().trim()
            val tahap = getString(R.string.pengepul)
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

            namaPengepul.showErrorIfEmpty(
                binding.edtNamaPengepul,
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
                    namaPengepul,
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
                            this@TahapPengepulActivity,
                            getString(R.string.berhasil_menambah_data, Utils.PENGEPUL),
                            Toast.LENGTH_SHORT
                        ).show()
                        when (tahapSelanjutnya) {
                            Utils.GUDANG -> {
                                val intentGudang = Intent(this@TahapPengepulActivity, TahapGudangActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentGudang)
                                finish()

                            }
                            Utils.TENGKULAK -> {
                                val intentTengkulak = Intent(this@TahapPengepulActivity, TahapTengkulakActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentTengkulak)
                                finish()
                            }
                            Utils.PENGGILING -> {
                                val intentPenggiling = Intent(this@TahapPengepulActivity, TahapPenggilingActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPenggiling)
                                finish()
                            }
                            Utils.PABRIK_PENGOLAHAN -> {
                                val intentPabrikPengolahan = Intent(this@TahapPengepulActivity, TahapPabrikPengolahanActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPabrikPengolahan)
                                finish()
                            }
                            Utils.PENERIMA -> {
                                val intentPenerima = Intent(this@TahapPengepulActivity, TahapPenerimaActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPenerima)
                                finish()
                            }
                            getString(R.string.selesai) -> {
                                finish()
                            }
                        }
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@TahapPengepulActivity,
                        getString(R.string.gagal_menambah_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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