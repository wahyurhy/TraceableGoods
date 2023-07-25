package com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahappenerima

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
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPenerimaBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.TahapAlurDistribusiActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahPenerimaActivity
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty

class TahapPenerimaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private var isTambahDataPenerimaClicked: Boolean = false
    private lateinit var adapterPenerima: ArrayAdapter<String>
    private var penerimaList = ArrayList<String>()
    private var mapPenerima: MutableMap<String, String> = mutableMapOf()

    private lateinit var binding: ActivityTahapPenerimaBinding
    private lateinit var viewModel: TahapPenerimaViewModel

    private var selectedSatuanYangDiterima = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPenerimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this) [TahapPenerimaViewModel::class.java]

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        viewModel.apply {
            loadDataPenerima(traceableGoodHelper)
        }

        viewModel.penerimaList.observe(this) { penerima ->
            if (penerima.isNotEmpty()) {
                penerima.forEach {
                    try {
                        penerimaList.add("${it.namaPenerima} - ${it.kontakPenerima.substring(0, 3)}***${it.kontakPenerima.substring(it.kontakPenerima.length - 3)}")
                        mapPenerima["${it.namaPenerima} - ${it.kontakPenerima.substring(0, 3)}***${it.kontakPenerima.substring(it.kontakPenerima.length - 3)}"] = it.kategoriPenerima
                    } catch (e: Exception) {
                        penerimaList.add(it.namaPenerima)
                        mapPenerima[it.namaPenerima] = it.kategoriPenerima
                        Log.e("TahapPenerimaActivity", "Error: ${e.message}")
                    }
                }
                adapterPenerima = ArrayAdapter<String>(
                    this@TahapPenerimaActivity,
                    android.R.layout.simple_list_item_1,
                    penerimaList
                )
                binding.edtNamaPenerima.setAdapter(adapterPenerima)
            } else {
                penerimaList = ArrayList()
                Toast.makeText(
                    this@TahapPenerimaActivity,
                    "Tidak ada data saat ini",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        viewModel.apply {
            isTambahPenerimaClicked.observe(this@TahapPenerimaActivity) {
                isTambahDataPenerimaClicked = it
            }
        }

        fitStatusBar()

        initClickListener()
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnTambahPenerima.setOnClickListener {
            val intentTambahPenerima = Intent(this, TambahPenerimaActivity::class.java).apply {
                val namaPenerima = binding.edtNamaPenerima.text.toString()
                putExtra(Utils.EXTRA_NAMA_PENERIMA, namaPenerima)
            }
            startActivity(intentTambahPenerima)
            viewModel.setTambahPenerimaClicked(true)
        }

        binding.satuanDiterimaSpinner.onItemSelectedListener = this

        binding.btnSelesai.setOnClickListener {
            binding.apply {
                val satuanYangDiterima = selectedSatuanYangDiterima
                val status = getString(R.string.selesai)
                val namaPenerima = edtNamaPenerima.text.toString().trim()
                val tahap = getString(R.string.penerima)
                val totalYangDiterima = edtTotalYangDiterima.text.toString().trim()

                val transaksiIdExtra = intent.getIntExtra(Utils.EXTRA_TRANSAKSI_ID, 0)
                val batchIdExtra = intent.getStringExtra(Utils.EXTRA_BATCH_ID) ?: ""
                val jenisProdukExtra = intent.getStringExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI) ?: ""
                val namaProdukExtra = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI) ?: ""
                val produkBatchExtra = intent.getStringExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI) ?: ""

                val kategoriPenerima = mapPenerima[namaPenerima] ?: tahap

                isAllSet = namaPenerima.isEmpty(
                    binding.edtNamaPenerima,
                    getString(R.string.tidak_boleh_kosong)
                )
                isAllSet = totalYangDiterima.isEmpty(
                    binding.edtTotalYangDiterima,
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
                        kategoriPenerima,
                        getCurrentDate() + " WIB"
                    )
                    val resultAlurTransaksi = traceableGoodHelper.insertAlurDistribusi(
                        batchIdExtra,
                        tahap,
                        status,
                        namaPenerima,
                        "",
                        "",
                        namaProdukExtra,
                        "$totalYangDiterima $satuanYangDiterima",
                        kategoriPenerima,
                        "",
                        "",
                        "",
                        "",
                        getCurrentDate() + " WIB"
                    )

                    if (resultTransaksi > 0) {
                        if (resultAlurTransaksi > 0) {
                            Toast.makeText(
                                this@TahapPenerimaActivity,
                                getString(R.string.berhasil_menambah_data, Utils.PABRIK_PENGOLAHAN),
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@TahapPenerimaActivity, TahapAlurDistribusiActivity::class.java).apply {
                                val batchId = intent.getStringExtra(Utils.EXTRA_BATCH_ID) ?: ""
                                val jenisProduk = intent.getStringExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI) ?: ""
                                val namaProduk = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI) ?: ""
                                val produkBatch = intent.getStringExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI) ?: ""
                                putExtra(Utils.EXTRA_BATCH_ID, batchId)
                                putExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI, jenisProduk)
                                putExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI, namaProduk)
                                putExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI, produkBatch)
                                putExtra(Utils.EXTRA_STATUS_TRANSAKSI, status)
                                putExtra(Utils.EXTRA_AFTER_TAHAP_PENERIMA, status)
                            }
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@TahapPenerimaActivity,
                            getString(R.string.gagal_menambah_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
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
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onResume() {
        super.onResume()
        if (isTambahDataPenerimaClicked) {
            penerimaList = ArrayList()
            viewModel.loadDataPenerima(traceableGoodHelper)
        }
    }

}