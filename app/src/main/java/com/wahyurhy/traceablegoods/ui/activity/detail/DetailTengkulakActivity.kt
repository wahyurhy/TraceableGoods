package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailTengkulakBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_TENGKULAK_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailTengkulakActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var isEdit = true

    private lateinit var binding: ActivityDetailTengkulakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTengkulakBinding.inflate(layoutInflater)
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
                edtNamaTengkulak.isEnabled = true
                edtKontakTengkulak.isEnabled = true
                edtAlamatTengkulak.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val tengkulakId = intent.getIntExtra(EXTRA_TENGKULAK_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                val namaTengkulak = edtNamaTengkulak.text.toString().trim()
                val kontakTengkulak = edtKontakTengkulak.text.toString().trim()
                val alamatTengkulak = edtAlamatTengkulak.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updateTengkulak(
                        tengkulakId.toString(),
                        namaTengkulak,
                        alamatTengkulak,
                        kontakTengkulak,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailTengkulakActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailTengkulakActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val tengkulakId = intent.getIntExtra(EXTRA_TENGKULAK_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailTengkulakActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deleteTengkulak(tengkulakId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailTengkulakActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailTengkulakActivity,
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
        val tengkulakId = intent.getIntExtra(EXTRA_TENGKULAK_ID, 0)
        val namaTengkulak = intent.getStringExtra(Utils.EXTRA_NAMA_TENGKULAK)
        val kontakTengkulak = intent.getStringExtra(Utils.EXTRA_KONTAK_TENGKULAK)
        val alamatTengkulak = intent.getStringExtra(Utils.EXTRA_ALAMAT_TENGKULAK)

        binding.idTengkulak.text = getString(R.string.id_tengkulak, tengkulakId)
        binding.edtNamaTengkulak.setText(namaTengkulak)
        binding.edtKontakTengkulak.setText(kontakTengkulak)
        binding.edtAlamatTengkulak.setText(alamatTengkulak)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}