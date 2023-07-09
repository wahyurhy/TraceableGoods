package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailPabrikPengolahanBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PABRIK_PENGOLAHAN_ID
import java.text.SimpleDateFormat
import java.util.*

class DetailPabrikPengolahanActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var isEdit = true

    private lateinit var binding: ActivityDetailPabrikPengolahanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPabrikPengolahanBinding.inflate(layoutInflater)
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
                edtNamaPabrikPengolahan.isEnabled = true
                edtKontakPabrikPengolahan.isEnabled = true
                edtAlamatPabrikPengolahan.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val pabrikId = intent.getIntExtra(EXTRA_PABRIK_PENGOLAHAN_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                val namaPabrikPengolahan = edtNamaPabrikPengolahan.text.toString().trim()
                val kontakPabrikPengolahan = edtKontakPabrikPengolahan.text.toString().trim()
                val alamatPabrikPengolahan = edtAlamatPabrikPengolahan.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updatePabrikPengolahan(
                        pabrikId.toString(),
                        namaPabrikPengolahan,
                        alamatPabrikPengolahan,
                        kontakPabrikPengolahan,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailPabrikPengolahanActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailPabrikPengolahanActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val pabrikId = intent.getIntExtra(EXTRA_PABRIK_PENGOLAHAN_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailPabrikPengolahanActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deletePabrikPengolahan(pabrikId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailPabrikPengolahanActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailPabrikPengolahanActivity,
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

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy - HH:mm", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    private fun initDataExtras() {
        val pabrikId = intent.getIntExtra(EXTRA_PABRIK_PENGOLAHAN_ID, 0)
        val namaPabrikPengolahan = intent.getStringExtra(Utils.EXTRA_NAMA_PABRIK_PENGOLAHAN)
        val kontakPabrikPengolahan = intent.getStringExtra(Utils.EXTRA_KONTAK_PABRIK_PENGOLAHAN)
        val alamatPabrikPengolahan = intent.getStringExtra(Utils.EXTRA_ALAMAT_PABRIK_PENGOLAHAN)

        Toast.makeText(this, "kategori pabrik: $pabrikId", Toast.LENGTH_SHORT).show()
        binding.idPabrikPengolahan.text = getString(R.string.id_pabrik_pengolahan, pabrikId)
        binding.edtNamaPabrikPengolahan.setText(namaPabrikPengolahan)
        binding.edtKontakPabrikPengolahan.setText(kontakPabrikPengolahan)
        binding.edtAlamatPabrikPengolahan.setText(alamatPabrikPengolahan)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}