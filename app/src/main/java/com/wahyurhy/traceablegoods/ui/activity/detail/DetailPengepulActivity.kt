package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailPengepulBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGEPUL_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailPengepulActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var isEdit = true

    private lateinit var binding: ActivityDetailPengepulBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPengepulBinding.inflate(layoutInflater)
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
                edtNamaPengepul.isEnabled = true
                edtKontakPengepul.isEnabled = true
                edtAlamatPengepul.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val pengepulId = intent.getIntExtra(EXTRA_PENGEPUL_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                val namaPengepul = edtNamaPengepul.text.toString().trim()
                val kontakPengepul = edtKontakPengepul.text.toString().trim()
                val alamatPengepul = edtAlamatPengepul.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updatePengepul(
                        pengepulId.toString(),
                        namaPengepul,
                        alamatPengepul,
                        kontakPengepul,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailPengepulActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailPengepulActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val pengepulId = intent.getIntExtra(EXTRA_PENGEPUL_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailPengepulActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deletePengepul(pengepulId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailPengepulActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailPengepulActivity,
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
        val pengepulId = intent.getIntExtra(EXTRA_PENGEPUL_ID, 0)
        val namaPengepul = intent.getStringExtra(Utils.EXTRA_NAMA_PENGEPUL)
        val kontakPengepul = intent.getStringExtra(Utils.EXTRA_KONTAK_PENGEPUL)
        val alamatPengepul = intent.getStringExtra(Utils.EXTRA_ALAMAT_PENGEPUL)

        Toast.makeText(this, "kategori pengepul: $pengepulId", Toast.LENGTH_SHORT).show()
        binding.idPengepul.text = getString(R.string.id_pengepul, pengepulId)
        binding.edtNamaPengepul.setText(namaPengepul)
        binding.edtKontakPengepul.setText(kontakPengepul)
        binding.edtAlamatPengepul.setText(alamatPengepul)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}