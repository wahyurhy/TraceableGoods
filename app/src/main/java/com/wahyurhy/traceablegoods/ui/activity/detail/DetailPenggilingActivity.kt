package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailPenggilingBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PENGGILING_ID
import java.text.SimpleDateFormat
import java.util.*

class DetailPenggilingActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var isEdit = true

    private lateinit var binding: ActivityDetailPenggilingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenggilingBinding.inflate(layoutInflater)
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
                edtNamaPenggiling.isEnabled = true
                edtKontakPenggiling.isEnabled = true
                edtAlamatPenggiling.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val penggilingId = intent.getIntExtra(EXTRA_PENGGILING_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                val namaPenggiling = edtNamaPenggiling.text.toString().trim()
                val kontakPenggiling = edtKontakPenggiling.text.toString().trim()
                val alamatPenggiling = edtAlamatPenggiling.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updatePenggiling(
                        penggilingId.toString(),
                        namaPenggiling,
                        alamatPenggiling,
                        kontakPenggiling,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailPenggilingActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailPenggilingActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val penggilingId = intent.getIntExtra(EXTRA_PENGGILING_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailPenggilingActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deletePenggiling(penggilingId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailPenggilingActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailPenggilingActivity,
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
        val penggilingId = intent.getIntExtra(EXTRA_PENGGILING_ID, 0)
        val namaPenggiling = intent.getStringExtra(Utils.EXTRA_NAMA_PENGGILING)
        val kontakPenggiling = intent.getStringExtra(Utils.EXTRA_KONTAK_PENGGILING)
        val alamatPenggiling = intent.getStringExtra(Utils.EXTRA_ALAMAT_PENGGILING)

        Toast.makeText(this, "kategori penggiling: $penggilingId", Toast.LENGTH_SHORT).show()
        binding.idPenggiling.text = getString(R.string.id_penggiling, penggilingId)
        binding.edtNamaPenggiling.setText(namaPenggiling)
        binding.edtKontakPenggiling.setText(kontakPenggiling)
        binding.edtAlamatPenggiling.setText(alamatPenggiling)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}