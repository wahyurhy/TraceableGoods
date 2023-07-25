package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailGudangBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_GUDANG_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailGudangActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var isEdit = true

    private lateinit var binding: ActivityDetailGudangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGudangBinding.inflate(layoutInflater)
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
                edtNamaGudang.isEnabled = true
                edtKontakGudang.isEnabled = true
                edtAlamatGudang.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val gudangId = intent.getIntExtra(EXTRA_GUDANG_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                val namaGudang = edtNamaGudang.text.toString().trim()
                val kontakGudang = edtKontakGudang.text.toString().trim()
                val alamatGudang = edtAlamatGudang.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updateGudang(
                        gudangId.toString(),
                        namaGudang,
                        alamatGudang,
                        kontakGudang,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailGudangActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailGudangActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val gudangId = intent.getIntExtra(EXTRA_GUDANG_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailGudangActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deleteGudang(gudangId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailGudangActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailGudangActivity,
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
        val gudangId = intent.getIntExtra(EXTRA_GUDANG_ID, 0)
        val namaGudang = intent.getStringExtra(Utils.EXTRA_NAMA_GUDANG)
        val kontakGudang = intent.getStringExtra(Utils.EXTRA_KONTAK_GUDANG)
        val alamatGudang = intent.getStringExtra(Utils.EXTRA_ALAMAT_GUDANG)

        binding.idGudang.text = getString(R.string.id_gudang, gudangId)
        binding.edtNamaGudang.setText(namaGudang)
        binding.edtKontakGudang.setText(kontakGudang)
        binding.edtAlamatGudang.setText(alamatGudang)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}