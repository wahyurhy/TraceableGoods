package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapprodusen

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
import com.wahyurhy.traceablegoods.databinding.ActivityTahapProdusenBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahDistributorActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahProdukActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahProdusenActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapgudang.TahapGudangActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappabrikpengolahan.TahapPabrikPengolahanActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenerima.TahapPenerimaActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappengepul.TahapPengepulActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenggiling.TahapPenggilingActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahaptengkulak.TahapTengkulakActivity
import com.wahyurhy.traceablegoods.utils.Lokasi
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty
import java.text.SimpleDateFormat
import java.util.*

class TahapProdusenActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private var isTambahDataProdusenClicked: Boolean = false
    private var isTambahDataProdukClicked: Boolean = false
    private var isTambahDataDistributorClicked: Boolean = false
    private lateinit var adapterProdusen: ArrayAdapter<String>
    private lateinit var adapterProduk: ArrayAdapter<String>
    private lateinit var adapterDistributor: ArrayAdapter<String>
    private lateinit var adapterLokasi: ArrayAdapter<String>
    private var produsenList = ArrayList<String>()
    private var produkList = ArrayList<String>()
    private var lokasiArray = Lokasi.lokasi
    private var distributorList = ArrayList<String>()
    private var mapProdusen: MutableMap<String, String> = mutableMapOf()
    private var mapProduk: MutableMap<String, String> = mutableMapOf()

    private lateinit var binding: ActivityTahapProdusenBinding
    private lateinit var viewModel: TahapProdusenViewModel

    private var selectedTahapSelanjutnya = ""
    private var selectedSatuan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapProdusenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TahapProdusenViewModel::class.java]

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        viewModel.apply {
            loadDataProdusen(traceableGoodHelper)
            loadDataProduk(traceableGoodHelper)
            loadDataDistributor(traceableGoodHelper)
        }

        viewModel.produsenList.observe(this) { produsen ->
            if (produsen.isNotEmpty()) {
                produsen.forEach {
                    try {
                        produsenList.add(
                            "${it.namaProdusen} - ${
                                it.noNpwp.substring(
                                    0,
                                    3
                                )
                            }***${it.noNpwp.substring(it.noNpwp.length - 3)}"
                        )
                        mapProdusen["${it.namaProdusen} - ${
                            it.noNpwp.substring(
                                0,
                                3
                            )
                        }***${it.noNpwp.substring(it.noNpwp.length - 3)}"] = it.kategoriProdusen
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
                binding.edtNamaProdusen.setAdapter(adapterProdusen)
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

        viewModel.produkList.observe(this) { produk ->
            if (produk.isNotEmpty()) {
                produk.forEach {
                    produkList.add(it.namaProduk)
                    mapProduk[it.namaProduk] = it.jenisProduk
                }
                adapterProduk = ArrayAdapter<String>(
                    this@TahapProdusenActivity,
                    android.R.layout.simple_list_item_1,
                    produkList
                )
                binding.edtNamaProduk.setAdapter(adapterProduk)
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
                        Log.e("TahapProdusenActivity", "Error: ${e.message}")
                    }
                }
                adapterDistributor = ArrayAdapter<String>(
                    this@TahapProdusenActivity,
                    android.R.layout.simple_list_item_1,
                    distributorList
                )
                binding.edtNamaDistributor.setAdapter(adapterDistributor)
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

        viewModel.apply {
            isTambahProdusenClicked.observe(this@TahapProdusenActivity) {
                isTambahDataProdusenClicked = it
            }
            isTambahProdukClicked.observe(this@TahapProdusenActivity) {
                isTambahDataProdukClicked = it
            }
            isTambahDistributorClicked.observe(this@TahapProdusenActivity) {
                isTambahDataDistributorClicked = it
            }
        }

        initAdapterLokasi()

        fitStatusBar()

        initClickListener()
    }

    private fun initAdapterLokasi() {
        adapterLokasi = ArrayAdapter<String>(
            this@TahapProdusenActivity,
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

        binding.btnTambahProdusen.setOnClickListener {
            val intentTambahProdusen = Intent(this, TambahProdusenActivity::class.java).apply {
                val namaProdusen = binding.edtNamaProdusen.text.toString()
                putExtra(Utils.EXTRA_NAMA_PRODUSEN, namaProdusen)
            }
            startActivity(intentTambahProdusen)
            viewModel.setTambahProdusenClicked(true)
        }

        binding.btnTambahProduk.setOnClickListener {
            val intentTambahProduk = Intent(this, TambahProdukActivity::class.java).apply {
                val namaProduk = binding.edtNamaProduk.text.toString()
                putExtra(Utils.EXTRA_NAMA_PRODUK, namaProduk)
            }
            startActivity(intentTambahProduk)
            viewModel.setTambahProdukClicked(true)
        }

        binding.btnTambahDistributor.setOnClickListener {
            val intentTambahDistributor = Intent(this, TambahDistributorActivity::class.java).apply {
                val namaDistributor = binding.edtNamaDistributor.text.toString()
                putExtra(Utils.EXTRA_NAMA_DISTRIBUTOR, namaDistributor)
            }
            startActivity(intentTambahDistributor)
            viewModel.setTambahDistributorClicked(true)
        }

        binding.tahapSelanjutnyaSpinner.onItemSelectedListener = this
        binding.satuanSpinner.onItemSelectedListener = this

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
            val satuan = selectedSatuan
            val status = selectedTahapSelanjutnya
            val namaProdusen = edtNamaProdusen.text.toString().trim()
            val namaProduk = edtNamaProduk.text.toString().trim()
            val tahap = mapProdusen[namaProdusen] ?: Utils.PRODUSEN
            val jenisProduk = mapProduk[namaProduk] ?: Utils.BIJIAN
            val namaDistributor = edtNamaDistributor.text.toString().trim()
            val totalYangDiDistribusikan = edtTotalYangDidistribusikan.text.toString().trim()
            val lokasiAsal = edtLokasiAsal.text.toString().trim()
            val lokasiTujuan = edtLokasiTujuan.text.toString().trim()
            var batchId: String
            try {
                batchId = namaProdusen.substring(
                    0,
                    2
                ) + generateDateBatchId() + namaProdusen.length + lokasiAsal.substring(0, 3)
                    .uppercase().replace("[,./ ]".toRegex(), "")
            } catch (e: Exception) {
                batchId =
                    namaProdusen + generateDateBatchId() + namaProdusen.length + lokasiAsal.uppercase()
                        .replace("[,./ ]".toRegex(), "")
                Log.e("TahapProdusenActivity", "Error: ${e.message}")
            }
            val produkBatch = "$namaProduk - $batchId"

            isAllSet = namaProdusen.isEmpty(
                binding.edtNamaProdusen,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = namaProduk.isEmpty(
                binding.edtNamaProduk,
                getString(R.string.tidak_boleh_kosong)
            )
            isAllSet = namaDistributor.isEmpty(
                binding.edtNamaDistributor,
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
                        when (tahapSelanjutnya) {
                            Utils.GUDANG -> {
                                val intentGudang = Intent(this@TahapProdusenActivity, TahapGudangActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, resultTransaksi.toInt())
                                    putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
                                }
                                startActivity(intentGudang)
                                finish()

                            }
                            Utils.TENGKULAK -> {
                                val intentTengkulak = Intent(this@TahapProdusenActivity, TahapTengkulakActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, resultTransaksi.toInt())
                                    putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
                                }
                                startActivity(intentTengkulak)
                                finish()
                            }
                            Utils.PENGGILING -> {
                                val intentPenggiling = Intent(this@TahapProdusenActivity, TahapPenggilingActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, resultTransaksi.toInt())
                                    putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
                                }
                                startActivity(intentPenggiling)
                                finish()
                            }
                            Utils.PENGEPUL -> {
                                val intentPengepul = Intent(this@TahapProdusenActivity, TahapPengepulActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, resultTransaksi.toInt())
                                    putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
                                }
                                startActivity(intentPengepul)
                                finish()
                            }
                            Utils.PABRIK_PENGOLAHAN -> {
                                val intentPabrikPengolahan = Intent(this@TahapProdusenActivity, TahapPabrikPengolahanActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, resultTransaksi.toInt())
                                    putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
                                }
                                startActivity(intentPabrikPengolahan)
                                finish()
                            }
                            Utils.PENERIMA -> {
                                val intentPenerima = Intent(this@TahapProdusenActivity, TahapPenerimaActivity::class.java).apply {
                                    putExtra(Utils.EXTRA_TRANSAKSI_ID, resultTransaksi.toInt())
                                    putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                    putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                    putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                    putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
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
                        this@TahapProdusenActivity,
                        getString(R.string.gagal_menambah_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun generateDateBatchId(): String {
        val dateFormat = SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
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

    override fun onResume() {
        super.onResume()
        if (isTambahDataProdusenClicked) {
            produsenList = ArrayList<String>()
            mapProdusen = mutableMapOf()
            viewModel.loadDataProdusen(traceableGoodHelper)
        }
        if (isTambahDataProdukClicked) {
            produkList = ArrayList<String>()
            mapProduk = mutableMapOf()
            viewModel.loadDataProduk(traceableGoodHelper)
        }
        if (isTambahDataDistributorClicked) {
            distributorList = ArrayList<String>()
            viewModel.loadDataDistributor(traceableGoodHelper)
        }
    }

}