package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataProdusenBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate

class TambahProdusenActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private var selectedKategoriProdusen = ""

    private lateinit var binding: ActivityTambahDataProdusenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataProdusenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initEditText()
        initClickListener()
    }

    private fun initEditText() {
        val produsen = intent.getStringExtra(Utils.EXTRA_NAMA_PRODUSEN) ?: ""
        binding.edtNamaProdusen.setText(produsen)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.kategoriProdusenSpinner.onItemSelectedListener = this

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val kategoriProdusen = selectedKategoriProdusen
                val namaProdusen = edtNamaProdusen.text.toString().trim()
                val alamatProdusen = edtAlamatProdusen.text.toString().trim()
                val kontakProdusen = edtKontakProdusen.text.toString().trim()
                val noNPWPProdusen = edtNpwpProdusen.text.toString().trim()

                namaProdusen.showErrorIfEmpty(binding.edtNamaProdusen, getString(R.string.tidak_boleh_kosong))
                alamatProdusen.showErrorIfEmpty(binding.edtAlamatProdusen, getString(R.string.tidak_boleh_kosong))
                kontakProdusen.showErrorIfEmpty(binding.edtKontakProdusen, getString(R.string.tidak_boleh_kosong))
                noNPWPProdusen.showErrorIfEmpty(binding.edtNpwpProdusen, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(
                        Utils.PRODUSEN_ID,
                        Utils.PRODUSEN,
                        getCurrentDate() + " WIB"
                    )
                    val produsenId = traceableGoodHelper.insertProdusen(
                        Utils.PRODUSEN_ID,
                        namaProdusen,
                        kategoriProdusen,
                        alamatProdusen,
                        kontakProdusen,
                        noNPWPProdusen,
                        getCurrentDate() + " WIB"
                    )

                    if (produsenId > 0) {
                        Toast.makeText(
                            this@TambahProdusenActivity,
                            "Berhasil Tambah Data ${Utils.PRODUSEN}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahProdusenActivity,
                            "Gagal menambahkan data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        for (i in 0..2) {
            when (binding.kategoriProdusenSpinner.selectedItem) {
                resources.getStringArray(R.array.kategori_produsen_spinner)[i].toString() -> {
                    selectedKategoriProdusen =
                        resources.getStringArray(R.array.kategori_produsen_spinner)[i].toString()
                    Toast.makeText(this, "Hi $selectedKategoriProdusen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}