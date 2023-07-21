package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPenggilingBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.PENGGILING
import com.wahyurhy.traceablegoods.utils.Utils.PENGGILING_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty

class TambahPenggilingActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private lateinit var binding: ActivityTambahDataPenggilingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPenggilingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initEditText()
        initClickListener()
    }

    private fun initEditText() {
        val penggiling = intent.getStringExtra(Utils.EXTRA_NAMA_PENGGILING) ?: ""
        binding.edtNamaPenggiling.setText(penggiling)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val namaPenggiling = edtNamaPenggiling.text.toString().trim()
                val kontakPenggiling = edtKontakPenggiling.text.toString().trim()
                val alamatPenggiling = edtAlamatPenggiling.text.toString().trim()

                isAllSet = namaPenggiling.isEmpty(binding.edtNamaPenggiling, getString(R.string.tidak_boleh_kosong))
                isAllSet = namaPenggiling.isEmpty(binding.edtKontakPenggiling, getString(R.string.tidak_boleh_kosong))
                isAllSet = namaPenggiling.isEmpty(binding.edtAlamatPenggiling, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(PENGGILING_ID, PENGGILING, getCurrentDate() + " WIB")
                    val penggilingId = traceableGoodHelper.insertPenggiling(PENGGILING_ID, namaPenggiling, alamatPenggiling, kontakPenggiling, getCurrentDate() + " WIB")

                    if (penggilingId > 0) {
                        Toast.makeText(
                            this@TambahPenggilingActivity,
                            "Berhasil Tambah Data $PENGGILING",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahPenggilingActivity,
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

}