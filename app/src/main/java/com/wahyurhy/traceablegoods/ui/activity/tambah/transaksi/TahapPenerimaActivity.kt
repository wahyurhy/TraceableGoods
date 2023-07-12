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
import com.wahyurhy.traceablegoods.databinding.ActivityTahapPenerimaBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.ui.activity.TahapAlurDistribusiActivity
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TahapPenerimaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false
    private lateinit var adapterPenerima: ArrayAdapter<String>
    private var penerimaList = ArrayList<String>()
    private var mapPenerima: MutableMap<String, String> = mutableMapOf()

    private lateinit var binding: ActivityTahapPenerimaBinding

    private var selectedSatuanYangDiterima = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapPenerimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        loadDataPenerima()

        fitStatusBar()

        initClickListener()
    }

    private fun loadDataPenerima() {
        lifecycleScope.launch {
            binding.apply {
                val deferredPenerima = async(Dispatchers.IO) {
                    val cursor = traceableGoodHelper.queryAllPenerima()
                    MappingHelper.mapCursorToArrayListPenerima(cursor)
                }
                val penerima = deferredPenerima.await()
                if (penerima.size > 0) {
                    penerima.forEach {
                        try {
                            penerimaList.add("${it.namaPenerima} - ${it.kontakPenerima.substring(0, 3)}***${it.kontakPenerima.substring(it.kontakPenerima.length - 3)}")
                            mapPenerima["${it.namaPenerima} - ${it.kontakPenerima.substring(0, 3)}***${it.kontakPenerima.substring(it.kontakPenerima.length - 3)}"] = it.kategoriPenerima
                        } catch (e: Exception) {
                            penerimaList.add(it.namaPenerima)
                            mapPenerima[it.namaPenerima] = it.kategoriPenerima
                            Log.e("TahapPabrikActivity", "Error: ${e.message}")
                        }
                    }
                    adapterPenerima = ArrayAdapter<String>(
                        this@TahapPenerimaActivity,
                        android.R.layout.simple_list_item_1,
                        penerimaList
                    )
                    edtNamaPenerima.setAdapter(adapterPenerima)
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
        }
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.satuanDiterimaSpinner.onItemSelectedListener = this

        binding.btnSelesai.setOnClickListener {
            binding.apply {
                val satuanYangDiterima = selectedSatuanYangDiterima
                val status = getString(R.string.selesai)
                val namaPenerima = edtNamaPenerima.text.toString()
                val tahap = mapPenerima[namaPenerima] ?: ""
                val totalYangDiterima = edtTotalYangDiterima.text.toString().trim()

                val transaksiIdExtra = intent.getIntExtra(Utils.EXTRA_TRANSAKSI_ID, 0)
                val batchIdExtra = intent.getStringExtra(Utils.EXTRA_BATCH_ID) ?: ""
                val jenisProdukExtra = intent.getStringExtra(Utils.EXTRA_JENIS_PRODUK_TRANSAKSI) ?: ""
                val namaProdukExtra = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUK_TRANSAKSI) ?: ""
                val produkBatchExtra = intent.getStringExtra(Utils.EXTRA_PRODUK_BATCH_TRANSAKSI) ?: ""

                namaPenerima.showErrorIfEmpty(
                    binding.edtNamaPenerima,
                    getString(R.string.tidak_boleh_kosong)
                )
                totalYangDiterima.showErrorIfEmpty(
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
                        tahap,
                        getCurrentDate() + " WIB"
                    )
                    val resultAlurTransaksi = traceableGoodHelper.insertAlurDistribusi(
                        batchIdExtra,
                        tahap,
                        status,
                        namaPenerima,
                        "",
                        "",
                        "",
                        "$totalYangDiterima $satuanYangDiterima",
                        "",
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
                            startActivity(Intent(this@TahapPenerimaActivity, TahapAlurDistribusiActivity::class.java))
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

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}