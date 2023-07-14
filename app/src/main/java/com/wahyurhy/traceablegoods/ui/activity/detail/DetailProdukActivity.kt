package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailProdukBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DESKRIPSI_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_MEREK_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NO_LOT_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUK_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TGL_KADALUARSA_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TGL_PRODUKSI_PRODUK
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailProdukActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var selectedJenisProduk = ""
    private var isEdit = true

    private lateinit var binding: ActivityDetailProdukBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()

        initDataExtras()

        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnUbah.setOnClickListener {
                jenisProdukSpinner.isEnabled = true
                edtNamaProduk.isEnabled = true
                edtMerekProduk.isEnabled = true
                edtNoLotProduk.isEnabled = true
                edtTanggalProduksiProduk.isEnabled = true
                edtTanggalKadaluarsaProduk.isEnabled = true
                edtDeskripsiProduk.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val produkId = intent.getIntExtra(EXTRA_PRODUK_ID, 0)

                if (!isEdit) {
                    val merekProdukExtra = intent.getStringExtra(EXTRA_MEREK_PRODUK)
                    val noLotProdukExtra = intent.getStringExtra(EXTRA_NO_LOT_PRODUK)
                    val tanggalProduksiProdukExtra = intent.getStringExtra(EXTRA_TGL_PRODUKSI_PRODUK)
                    val tanggalKadaluarsaProdukExtra = intent.getStringExtra(EXTRA_TGL_KADALUARSA_PRODUK)
                    val deskripsiProdukExtra = intent.getStringExtra(EXTRA_DESKRIPSI_PRODUK)

                    edtMerekProduk.setText(merekProdukExtra)
                    edtNoLotProduk.setText(noLotProdukExtra)
                    edtTanggalProduksiProduk.setText(tanggalProduksiProdukExtra)
                    edtTanggalKadaluarsaProduk.setText(tanggalKadaluarsaProdukExtra)
                    edtDeskripsiProduk.setText(deskripsiProdukExtra)
                }

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                jenisProdukSpinner.onItemSelectedListener = this@DetailProdukActivity

                val jenisProduk = selectedJenisProduk
                val namaProduk = edtNamaProduk.text.toString().trim()
                val merekProduk = edtMerekProduk.text.toString().trim()
                val noLotProduk = edtNoLotProduk.text.toString().trim()
                val tanggalProduksiProduk = edtTanggalProduksiProduk.text.toString().trim()
                val tanggalKadaluarsaProduk = edtTanggalKadaluarsaProduk.text.toString().trim()
                val deskripsiProduk = edtDeskripsiProduk.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updateProduk(
                        produkId.toString(),
                        jenisProduk,
                        namaProduk,
                        merekProduk,
                        noLotProduk,
                        tanggalProduksiProduk,
                        tanggalKadaluarsaProduk,
                        deskripsiProduk,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailProdukActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailProdukActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val produkId = intent.getIntExtra(EXTRA_PRODUK_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailProdukActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deleteProduk(produkId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailProdukActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailProdukActivity,
                                getString(R.string.gagal_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton("Batalkan") { dialog, _ -> dialog.cancel() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }

    private fun initDataExtras() {
        val jenisProdukArray = resources.getStringArray(R.array.jenis_produk_spinner)

        val produkId = intent.getIntExtra(EXTRA_PRODUK_ID, 0)
        val jenisProduk = intent.getStringExtra(EXTRA_JENIS_PRODUK)
        val namaProduk = intent.getStringExtra(EXTRA_NAMA_PRODUK)
        var merekProduk = intent.getStringExtra(EXTRA_MEREK_PRODUK)
        var noLotProduk = intent.getStringExtra(EXTRA_NO_LOT_PRODUK)
        var tanggalProduksiProduk = intent.getStringExtra(EXTRA_TGL_PRODUKSI_PRODUK)
        var tanggalKadaluarsaProduk = intent.getStringExtra(EXTRA_TGL_KADALUARSA_PRODUK)
        var deskripsiProduk = intent.getStringExtra(EXTRA_DESKRIPSI_PRODUK)

        selectedJenisProduk = jenisProduk ?: Utils.BIJIAN

        merekProduk = merekProduk.orDefault(getString(R.string.belum_ada))
        noLotProduk = noLotProduk.orDefault(getString(R.string.belum_ada))
        tanggalProduksiProduk = tanggalProduksiProduk.orDefault(getString(R.string.belum_ada))
        tanggalKadaluarsaProduk = tanggalKadaluarsaProduk.orDefault(getString(R.string.belum_ada))
        deskripsiProduk = deskripsiProduk.orDefault(getString(R.string.belum_ada))

        val selectedPosition = jenisProdukArray.indexOf(jenisProduk)
        Toast.makeText(this, "jenis produk: $produkId", Toast.LENGTH_SHORT).show()
        binding.idProduk.text = getString(R.string.id_produk, produkId)
        binding.jenisProdukSpinner.setSelection(selectedPosition)
        binding.jenisProdukSpinner.isEnabled = false
        binding.edtNamaProduk.setText(namaProduk)
        binding.edtMerekProduk.setText(merekProduk)
        binding.edtNoLotProduk.setText(noLotProduk)
        binding.edtTanggalProduksiProduk.setText(tanggalProduksiProduk)
        binding.edtTanggalKadaluarsaProduk.setText(tanggalKadaluarsaProduk)
        binding.edtDeskripsiProduk.setText(deskripsiProduk)
    }

    private fun String?.orDefault(defaultValue: String): String {
        return if (this == null || this.isEmpty()) {
            defaultValue
        } else {
            this
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..3) {
            when (binding.jenisProdukSpinner.selectedItem) {
                resources.getStringArray(R.array.jenis_produk_spinner)[i].toString() -> {
                    selectedJenisProduk =
                        resources.getStringArray(R.array.jenis_produk_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedJenisProduk", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}