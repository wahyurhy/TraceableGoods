package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapgudang

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
import com.wahyurhy.traceablegoods.databinding.ActivityTahapGudangBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahDistributorActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahGudangActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappabrikpengolahan.TahapPabrikPengolahanActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenerima.TahapPenerimaActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappengepul.TahapPengepulActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenggiling.TahapPenggilingActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahaptengkulak.TahapTengkulakActivity
import com.wahyurhy.traceablegoods.utils.Lokasi
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_BATCH_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK_BATCH_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TRANSAKSI_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty
import com.wahyurhy.traceablegoods.utils.Utils.toRupiahFormat

class TahapGudangActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private var isTambahDataGudangClicked: Boolean = false
    private var isTambahDataDistributorClicked: Boolean = false
    private lateinit var adapterGudang: ArrayAdapter<String>
    private lateinit var adapterDistributor: ArrayAdapter<String>
    private lateinit var adapterLokasi: ArrayAdapter<String>
    private var gudangList = ArrayList<String>()
    private var distributorList = ArrayList<String>()
    private var lokasiArray = Lokasi.lokasi

    private lateinit var binding: ActivityTahapGudangBinding
    private lateinit var viewModel: TahapGudangViewModel

    private var selectedTahapSelanjutnya = ""
    private var selectedSatuanYangDiterima = ""
    private var selectedSatuanYangDiDistribusikan = ""
    private var selectedSatuanPerHarga = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapGudangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this) [TahapGudangViewModel::class.java]

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        viewModel.apply {
            loadDataGudang(traceableGoodHelper)
            loadDataDistributor(traceableGoodHelper)
        }

        viewModel.gudangList.observe(this) { gudang ->
            if (gudang.isNotEmpty()) {
                gudang.forEach {
                    try {
                        gudangList.add("${it.namaGudang} - ${it.kontakGudang.substring(0, 3)}***${it.kontakGudang.substring(it.kontakGudang.length - 3)}")
                    } catch (e: Exception) {
                        gudangList.add(it.namaGudang)
                        Log.e("TahapGudangActivity", "Error: ${e.message}")
                    }
                }
                adapterGudang = ArrayAdapter<String>(
                    this@TahapGudangActivity,
                    android.R.layout.simple_list_item_1,
                    gudangList
                )
                binding.edtNamaGudang.setAdapter(adapterGudang)
            } else {
                gudangList = ArrayList()
                Toast.makeText(
                    this@TahapGudangActivity,
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
                        distributorList.add("${it.namaDistributor} - ${it.nopolDistributor}")
                    } catch (e: Exception) {
                        distributorList.add(it.namaDistributor)
                        Log.e("TahapGudangActivity", "Error: ${e.message}")
                    }
                }
                adapterDistributor = ArrayAdapter<String>(
                    this@TahapGudangActivity,
                    android.R.layout.simple_list_item_1,
                    distributorList
                )
                binding.edtNamaDistributorSelanjutnya.setAdapter(adapterDistributor)
            } else {
                distributorList = ArrayList()
                Toast.makeText(
                    this@TahapGudangActivity,
                    "Tidak ada data saat ini",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        viewModel.apply {
            isTambahGudangClicked.observe(this@TahapGudangActivity) {
                isTambahDataGudangClicked = it
            }
            isTambahDistributorClicked.observe(this@TahapGudangActivity) {
                isTambahDataDistributorClicked = it
            }
        }

        initAdapterLokasi()

        fitStatusBar()

        initClickListener()
    }

    private fun initAdapterLokasi() {
        adapterLokasi = ArrayAdapter<String>(
            this@TahapGudangActivity,
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

        binding.btnTambahGudang.setOnClickListener {
            val intentTambahGudang = Intent(this, TambahGudangActivity::class.java).apply {
                val namaGudang = binding.edtNamaGudang.text.toString()
                putExtra(Utils.EXTRA_NAMA_GUDANG, namaGudang)
            }
            startActivity(intentTambahGudang)
            viewModel.setTambahGudangClicked(true)
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
        binding.satuanPerhargaSpinner.onItemSelectedListener = this

        binding.btnLanjut.setOnClickListener {
            when (selectedTahapSelanjutnya) {
                Utils.GUDANG -> {
                    Toast.makeText(
                        this,
                        getString(R.string.tahap_sedang_dikerjakan, selectedTahapSelanjutnya),
                        Toast.LENGTH_SHORT
                    ).show()
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
            val satuanPerHarga = selectedSatuanPerHarga
            val status = selectedTahapSelanjutnya
            val namaGudang = edtNamaGudang.text.toString().trim()
            val tahap = getString(R.string.gudang)
            val namaDistributorSelanjutnya =
                edtNamaDistributorSelanjutnya.text.toString().trim()
            val totalYangDiterima = edtTotalYangDiterima.text.toString().trim()
            val totalYangDiDistribusikan = edtTotalYangDidistribusikan.text.toString().trim()
            val hargaJual = edtHargaJual.text.toString().trim()
            val hargaJualRupiah = hargaJual.toDouble().toRupiahFormat().replace(",00", "")
            val lokasiAsal = edtLokasiAsal.text.toString().trim()
            val lokasiTujuan = edtLokasiTujuan.text.toString().trim()

            val transaksiIdExtra = intent.getIntExtra(EXTRA_TRANSAKSI_ID, 0)
            val batchIdExtra = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
            val jenisProdukExtra = intent.getStringExtra(EXTRA_JENIS_PRODUK_TRANSAKSI) ?: ""
            val namaProdukExtra = intent.getStringExtra(EXTRA_NAMA_PRODUK_TRANSAKSI) ?: ""
            val produkBatchExtra = intent.getStringExtra(EXTRA_PRODUK_BATCH_TRANSAKSI) ?: ""

            isAllSet = namaGudang.isEmpty(
                binding.edtNamaGudang,
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
                    "$hargaJualRupiah/$satuanPerHarga",
                    getCurrentDate() + " WIB"
                )
                val resultAlurTransaksi = traceableGoodHelper.insertAlurDistribusi(
                    batchIdExtra,
                    tahap,
                    status,
                    namaGudang,
                    "",
                    "",
                    "",
                    "$totalYangDiterima $satuanYangDiterima",
                    "",
                    namaDistributorSelanjutnya,
                    "$totalYangDiDistribusikan $satuanYangDiDistribusikan",
                    lokasiAsal,
                    lokasiTujuan,
                    "$hargaJualRupiah/$satuanPerHarga",
                    getCurrentDate() + " WIB"
                )

                if (resultTransaksi > 0) {
                    if (resultAlurTransaksi > 0) {
                        Toast.makeText(
                            this@TahapGudangActivity,
                            getString(R.string.berhasil_menambah_data, Utils.GUDANG),
                            Toast.LENGTH_SHORT
                        ).show()
                        when (tahapSelanjutnya) {
                            Utils.TENGKULAK -> {
                                val intentTengkulak = Intent(
                                    this@TahapGudangActivity,
                                    TahapTengkulakActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentTengkulak)
                                finish()
                            }
                            Utils.PENGGILING -> {
                                val intentPenggiling = Intent(
                                    this@TahapGudangActivity,
                                    TahapPenggilingActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPenggiling)
                                finish()
                            }
                            Utils.PENGEPUL -> {
                                val intentPengepul = Intent(
                                    this@TahapGudangActivity,
                                    TahapPengepulActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPengepul)
                                finish()
                            }
                            Utils.PABRIK_PENGOLAHAN -> {
                                val intentPabrikPengolahan = Intent(
                                    this@TahapGudangActivity,
                                    TahapPabrikPengolahanActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
                                }
                                startActivity(intentPabrikPengolahan)
                                finish()
                            }
                            Utils.PENERIMA -> {
                                val intentPenerima = Intent(
                                    this@TahapGudangActivity,
                                    TahapPenerimaActivity::class.java
                                ).apply {
                                    putExtra(EXTRA_TRANSAKSI_ID, transaksiIdExtra)
                                    putExtra(EXTRA_BATCH_ID, batchIdExtra)
                                    putExtra(EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProdukExtra)
                                    putExtra(EXTRA_NAMA_PRODUK_TRANSAKSI, namaProdukExtra)
                                    putExtra(EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatchExtra)
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
                        this@TahapGudangActivity,
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
                    selectedTahapSelanjutnya =
                        resources.getStringArray(R.array.tahap_spinner)[i].toString()
                }
            }
        }
        for (i in 0..10) {
            when (binding.satuanDiterimaSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuanYangDiterima =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() }
            }
            when (binding.satuanDiDistibusikanSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_produk_spinner)[i].toString() -> {
                    selectedSatuanYangDiDistribusikan =
                        resources.getStringArray(R.array.satuan_produk_spinner)[i].toString()
                }
            }
        }
        for (i in 0..14) {
            when (binding.satuanPerhargaSpinner.selectedItem) {
                resources.getStringArray(R.array.satuan_per_harga)[i].toString() -> {
                    selectedSatuanPerHarga =
                        resources.getStringArray(R.array.satuan_per_harga)[i].toString()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onResume() {
        super.onResume()
        if (isTambahDataGudangClicked) {
            gudangList = ArrayList()
            viewModel.loadDataGudang(traceableGoodHelper)
        }
        if (isTambahDataDistributorClicked) {
            distributorList = ArrayList()
            viewModel.loadDataDistributor(traceableGoodHelper)
        }
    }

}