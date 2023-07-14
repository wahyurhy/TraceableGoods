package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailPenerimaBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENERIMA_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailPenerimaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var selectedKategoriPenerima = ""
    private var isEdit = true

    private lateinit var binding: ActivityDetailPenerimaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenerimaBinding.inflate(layoutInflater)
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
                kategoriPenerimaSpinner.isEnabled = true
                edtNamaPenerima.isEnabled = true
                edtKontakPenerima.isEnabled = true
                edtAlamatPenerima.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val penerimaId = intent.getIntExtra(EXTRA_PENERIMA_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                kategoriPenerimaSpinner.onItemSelectedListener = this@DetailPenerimaActivity

                val kategoriPenerima = selectedKategoriPenerima
                val namaPenerima = edtNamaPenerima.text.toString().trim()
                val kontakPenerima = edtKontakPenerima.text.toString().trim()
                val alamatPenerima = edtAlamatPenerima.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updatePenerima(
                        penerimaId.toString(),
                        namaPenerima,
                        kategoriPenerima,
                        alamatPenerima,
                        kontakPenerima,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailPenerimaActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailPenerimaActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val penerimaId = intent.getIntExtra(EXTRA_PENERIMA_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailPenerimaActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deletePenerima(penerimaId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailPenerimaActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailPenerimaActivity,
                                getString(R.string.gagal_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            Toast.makeText(
                                this@DetailPenerimaActivity,
                                "result: $result",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    .setNegativeButton("Batalkan") { dialog, _ -> dialog.cancel() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }

    private fun initDataExtras() {
        val kategoriPenerimaArray = resources.getStringArray(R.array.kategori_penerima_spinner)

        val penerimaId = intent.getIntExtra(EXTRA_PENERIMA_ID, 0)
        val kategoriPenerima = intent.getStringExtra(Utils.EXTRA_KATEGORI_PENERIMA)
        val namaPenerima = intent.getStringExtra(Utils.EXTRA_NAMA_PENERIMA)
        val kontakPenerima = intent.getStringExtra(Utils.EXTRA_KONTAK_PENERIMA)
        val alamatPenerima = intent.getStringExtra(Utils.EXTRA_ALAMAT_PENERIMA)

        selectedKategoriPenerima = kategoriPenerima ?: getString(R.string.tokopengecer)


        val selectedPosition = kategoriPenerimaArray.indexOf(kategoriPenerima)
        Toast.makeText(this, "kategori penerima: $penerimaId", Toast.LENGTH_SHORT).show()
        binding.idPenerima.text = getString(R.string.id_penerima, penerimaId)
        binding.kategoriPenerimaSpinner.setSelection(selectedPosition)
        binding.kategoriPenerimaSpinner.isEnabled = false
        binding.edtNamaPenerima.setText(namaPenerima)
        binding.edtKontakPenerima.setText(kontakPenerima)
        binding.edtAlamatPenerima.setText(alamatPenerima)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..7) {
            when (binding.kategoriPenerimaSpinner.selectedItem) {
                resources.getStringArray(R.array.kategori_penerima_spinner)[i].toString() -> {
                    selectedKategoriPenerima =
                        resources.getStringArray(R.array.kategori_penerima_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedKategoriPenerima", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}