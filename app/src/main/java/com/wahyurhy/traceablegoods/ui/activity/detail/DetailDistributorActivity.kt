package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailDistributorBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_DISTRIBUTOR_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailDistributorActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var isEdit = true

    private lateinit var binding: ActivityDetailDistributorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDistributorBinding.inflate(layoutInflater)
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
                edtNamaDistributor.isEnabled = true
                edtKontakDistributor.isEnabled = true
                edtNopolDistributor.isEnabled = true
                edtTonaseDistributor.isEnabled = true
                edtAlamatDistributor.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val distributorId = intent.getIntExtra(EXTRA_DISTRIBUTOR_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                val namaDistributor = edtNamaDistributor.text.toString().trim()
                val kontakDistributor = edtKontakDistributor.text.toString().trim()
                val nopolDistributor = edtNopolDistributor.text.toString().trim()
                val tonaseDistributor = edtTonaseDistributor.text.toString().trim()
                val alamatDistributor = edtAlamatDistributor.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updateDistributor(
                        distributorId.toString(),
                        namaDistributor,
                        alamatDistributor,
                        kontakDistributor,
                        nopolDistributor,
                        tonaseDistributor,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailDistributorActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailDistributorActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val distributorId = intent.getIntExtra(EXTRA_DISTRIBUTOR_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailDistributorActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deleteDistributor(distributorId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailDistributorActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailDistributorActivity,
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
        val distributorId = intent.getIntExtra(EXTRA_DISTRIBUTOR_ID, 0)
        val namaDistributor = intent.getStringExtra(Utils.EXTRA_NAMA_DISTRIBUTOR)
        val kontakDistributor = intent.getStringExtra(Utils.EXTRA_KONTAK_DISTRIBUTOR)
        val nopolDistributor = intent.getStringExtra(Utils.EXTRA_NOPOL_DISTRIBUTOR)
        val tonaseDistributor = intent.getStringExtra(Utils.EXTRA_TONASE_DISTRIBUTOR)
        val alamatDistributor = intent.getStringExtra(Utils.EXTRA_ALAMAT_DISTRIBUTOR)

        binding.idDistributor.text = getString(R.string.id_distributor, distributorId)
        binding.edtNamaDistributor.setText(namaDistributor)
        binding.edtKontakDistributor.setText(kontakDistributor)
        binding.edtNopolDistributor.setText(nopolDistributor)
        binding.edtTonaseDistributor.setText(tonaseDistributor)
        binding.edtAlamatDistributor.setText(alamatDistributor)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}