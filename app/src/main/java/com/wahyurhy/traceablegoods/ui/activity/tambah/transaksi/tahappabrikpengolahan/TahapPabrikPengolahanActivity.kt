package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappabrikpengolahan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPabrikPengolahanBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahDistributorActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahPabrikPengolahanActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenerima.TahapPenerimaActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappengepul.TahapPengepulActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenggiling.TahapPenggilingActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahaptengkulak.TahapTengkulakActivity
import com.wahyurhy.traceablegoods.utils.Lokasi
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty

class TahapPabrikPengolahanActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private var isTambahDataPabrikPengolahanClicked: Boolean = false
    private var isTambahDataDistributorClicked: Boolean = false
    private lateinit var adapterPabrikPengolahan: ArrayAdapter<String>
    private lateinit var adapterDistributor: ArrayAdapter<String>
    private lateinit var adapterLokasi: ArrayAdapter<String>
    private var pabrikPengolahanList = ArrayList<String>()
    private var distributorList = ArrayList<String>()
    private var lokasiArray = Lokasi.lokasi

    private lateinit var binding: ActivityTahapPabrikPengolahanBinding
    private lateinit var viewModel: TahapPabrikPengolahanViewModel

    private var selectedTahapSelanjutnya = ""
    private var selectedSatuanYangDiterima = ""
    private var selectedSatuanYangDiDistribusikan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPabrikPengolahanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this) [TahapPabrikPengolahanViewModel::class.java]

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        viewModel.apply {
            loadDataPabrikPengolahan(traceableGoodHelper)
            loadDataDistributor(traceableGoodHelper)
        }

        viewModel.pabrikPengolahanList.observe(this) { pabrikPengolahan ->
            if (pabrikPengolahan.isNotEmpty()) {
                pabrikPengolahan.forEach {
                    try {
                        pabrikPengolahanList.add("${it.namaPabrikPengolahan} - ${it.kontakPabrikPengolahan.substring(0, 3)}***${it.kontakPabrikPengolahan.substring(it.kontakPabrikPengolahan.length - 3)}")
                    } catch (e: Exception) {
                        pabrikPengolahanList.add(it.namaPabrikPengolahan)
                        Log.e("TahapPabrikPengolahan", "Error: ${e.message}")
                    }
                }
                adapterPabrikPengolahan = ArrayAdapter<String>(
                    this@TahapPabrikPengolahanActivity,
                    android.R.layout.simple_list_item_1,
                    pabrikPengolahanList
                )
                binding.edtNamaPabrikPengolahan.setAdapter(adapterPabrikPengolahan)
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

        viewModel.distributorList.observe(this) { distributor ->
            if (distributor.isNotEmpty()) {
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
                        Log.e("TahapPabrikPengolahan", "Error: ${e.message}")
                    }
                }
                adapterDistributor = ArrayAdapter<String>(
                    this@TahapPabrikPengolahanActivity,
                    android.R.layout.simple_list_item_1,
                    distributorList
                )
                binding.edtNamaDistributorSelanjutnya.setAdapter(adapterDistributor)
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

        viewModel.apply {
            isTambahPabrikPengolahanClicked.observe(this@TahapPabrikPengolahanActivity) {
                isTambahDataPabrikPengolahanClicked = it
            }
            isTambahDistributorClicked.observe(this@TahapPabrikPengolahanActivity) {
                isTambahDataDistributorClicked = it
            }
        }

        initAdapterLokasi()

        fitStatusBar()

        initClickListener()
    }

    private fun initAdapterLokasi() {
        adapterLokasi = ArrayAdapter<String>(
            this@TahapPabrikPengolahanActivity,
            android.R.layout.simple_list_item_1,
            lokasiArray
        )
        binding.edtLokasiAsal.setAdapter(adapterLokasi)
        binding.edtLokasiTujuan.setAdapter(adapterLokasi)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnTambahPabrikPengolahan.setOnClickListener {
            val intentTambahPabrikPengolahan = Intent(this, TambahPabrikPengolahanActivity::class.java).apply {
                val namaPabrikPengolahan = binding.edtNamaPabrikPengolahan.text.toString()
                putExtra(Utils.EXTRA_NAMA_PABRIK_PENGOLAHAN, namaPabrikPengolahan)
            }
            startActivity(intentTambahPabrikPengolahan)
            viewModel.setTambahPabrikPengolahanClicked(true)
        }

        binding.btnTambahDistributor.setOnClickListener {
            val intentTambahDistributor = Intent(this, TambahDistributorActivity::class.java).apply {
                val namaDistributor = binding.edtNamaDistributorSelanjutnya.text.toString()
                putExtra(Utils.EXTRA_NAMA_DISTRIBUTOR, namaDistributor)
            }
            startActivity(intentTambahDistributor)
            viewModel.setTambahDistributorClicked(true)
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
                    simpanData(Utils.PENGEPUL)
                }
                Utils.PABRIK_PENGOLAHAN -> {
                    Toast.makeText(
                        this,
                        getString(R.string.tahap_sedang_dikerjakan, selectedTahapSelanjutnya),
                        Toast.LENGTH_SHORT
                    ).show()
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
            val namaPabrikPengolahan = edtNamaPabrikPengolahan.text.toString().trim()
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

            isAllSet = namaPabrikPengolahan.isEmpty(
                binding.edtNamaPabrikPengolahan,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = namaDistributorSelanjutnya.isEmpty(
                binding.edtNamaDistributorSelanjutnya,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = totalYangDiterima.isEmpty(
                binding.edtTotalYangDiterima,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = totalYangDiDistribusikan.isEmpty(
                binding.edtTotalYangDidistribusikan,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = lokasiAsal.isEmpty(
                binding.edtLokasiAsal,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = lokasiTujuan.isEmpty(
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
                        when (tahapSelanjutnya) {
                            Utils.GUDANG -> {
                                val intentPabrikPengolahan = Intent(this@TahapPabrikPengolahanActivity, TahapPabrikPengolahanActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPabrikPengolahan)
                                finish()

                            }
                            Utils.TENGKULAK -> {
                                val intentTengkulak = Intent(this@TahapPabrikPengolahanActivity, TahapTengkulakActivity::class.java).apply {
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
                                val intentPenggiling = Intent(this@TahapPabrikPengolahanActivity, TahapPenggilingActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPenggiling)
                                finish()
                            }
                            Utils.PENGEPUL -> {
                                val intentPengepul = Intent(this@TahapPabrikPengolahanActivity, TahapPengepulActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(Utils.EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPengepul)
                                finish()
                            }
                            Utils.PENERIMA -> {
                                val intentPenerima = Intent(this@TahapPabrikPengolahanActivity, TahapPenerimaActivity::class.java).apply {
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
                        this@TahapPabrikPengolahanActivity,
                        getString(R.string.gagal_menambah_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
        for (i in 0..10) {
            when (binding.satuanDiterimaSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuanYangDiterima =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString()
                }
            }
            when (binding.satuanDiDistibusikanSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuanYangDiDistribusikan =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onResume() {
        super.onResume()
        if (isTambahDataPabrikPengolahanClicked) {
            pabrikPengolahanList = ArrayList()
            viewModel.loadDataPabrikPengolahan(traceableGoodHelper)
        }
        if (isTambahDataDistributorClicked) {
            distributorList = ArrayList()
            viewModel.loadDataDistributor(traceableGoodHelper)
        }
    }

}