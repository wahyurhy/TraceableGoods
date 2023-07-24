package com.wahyurhy.traceablegoods.ui.activity.detail

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityDetailProdusenBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_PRODUSEN_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class DetailProdusenActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper

    private var selectedKategoriProdusen = ""
    private var isEdit = true

    private lateinit var binding: ActivityDetailProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProdusenBinding.inflate(layoutInflater)
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
                kategoriProdusenSpinner.isEnabled = true
                edtNamaProdusen.isEnabled = true
                edtNpwpProdusen.isEnabled = true
                edtKontakProdusen.isEnabled = true
                edtAlamatProdusen.isEnabled = true

                isEdit = !isEdit
                btnUbah.text = getString(R.string.simpan)
                tvDetailTitle.text = getString(R.string.ubah_data)
                val produsenId = intent.getIntExtra(EXTRA_PRODUSEN_ID, 0)

                btnUbah.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_selesai, null),
                    null
                )

                kategoriProdusenSpinner.onItemSelectedListener = this@DetailProdusenActivity

                val kategoriProdusen = selectedKategoriProdusen
                val namaProdusen = edtNamaProdusen.text.toString().trim()
                val npwpProdusen = edtNpwpProdusen.text.toString().trim()
                val kontakProdusen = edtKontakProdusen.text.toString().trim()
                val alamatProdusen = edtAlamatProdusen.text.toString().trim()
                if (isEdit) {
                    val result = traceableGoodHelper.updateProdusen(
                        produsenId.toString(),
                        namaProdusen,
                        kategoriProdusen,
                        alamatProdusen,
                        kontakProdusen,
                        npwpProdusen,
                        getCurrentDate() + " WIB"
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailProdusenActivity,
                            getString(R.string.berhasil_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailProdusenActivity,
                            getString(R.string.gagal_simpan_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnHapus.setOnClickListener {
                val produsenId = intent.getIntExtra(EXTRA_PRODUSEN_ID, 0)
                val alertDialogBuilder = AlertDialog.Builder(this@DetailProdusenActivity)

                alertDialogBuilder.setTitle(getString(R.string.hapus_data))
                alertDialogBuilder
                    .setMessage(getString(R.string.message_hapus_data))
                    .setCancelable(false)
                    .setPositiveButton("Ya") { _, _ ->
                        val result = traceableGoodHelper.deleteProdusen(produsenId.toString())
                        if (result > 0) {
                            Toast.makeText(
                                this@DetailProdusenActivity,
                                getString(R.string.berhasil_hapus_data),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@DetailProdusenActivity,
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
        val kategoriProdusenArray = resources.getStringArray(R.array.kategori_produsen_spinner)

        val produsenId = intent.getIntExtra(EXTRA_PRODUSEN_ID, 0)
        val kategoriProdusen = intent.getStringExtra(Utils.EXTRA_KATEGORI_PRODUSEN)
        val namaProdusen = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUSEN)
        val npwpProdusen = intent.getStringExtra(Utils.EXTRA_NPWP_PRODUSEN)
        val kontakProdusen = intent.getStringExtra(Utils.EXTRA_KONTAK_PRODUSEN)
        val alamatProdusen = intent.getStringExtra(Utils.EXTRA_ALAMAT_PRODUSEN)

        selectedKategoriProdusen = kategoriProdusen ?: getString(R.string.petani)


        val selectedPosition = kategoriProdusenArray.indexOf(kategoriProdusen)

        binding.idProdusen.text = getString(R.string.id_produsen, produsenId)
        binding.kategoriProdusenSpinner.setSelection(selectedPosition)
        binding.kategoriProdusenSpinner.isEnabled = false
        binding.edtNamaProdusen.setText(namaProdusen)
        binding.edtNpwpProdusen.setText(npwpProdusen)
        binding.edtKontakProdusen.setText(kontakProdusen)
        binding.edtAlamatProdusen.setText(alamatProdusen)
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..2) {
            when (binding.kategoriProdusenSpinner.selectedItem) {
                resources.getStringArray(R.array.kategori_produsen_spinner)[i].toString() -> {
                    selectedKategoriProdusen =
                        resources.getStringArray(R.array.kategori_produsen_spinner)[i].toString()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}