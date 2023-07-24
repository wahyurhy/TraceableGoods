package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataPengepulBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty

class TambahPengepulActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private lateinit var binding: ActivityTambahDataPengepulBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataPengepulBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initEditText()
        initClickListener()
    }

    private fun initEditText() {
        val pengepul = intent.getStringExtra(Utils.EXTRA_NAMA_PENGEPUL) ?: ""
        binding.edtNamaPengepul.setText(pengepul)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val namaPengepul = edtNamaPengepul.text.toString().trim()
                val kontakPengepul = edtKontakPengepul.text.toString().trim()
                val alamatPengepul = edtAlamatPengepul.text.toString().trim()

                isAllSet = namaPengepul.isEmpty(binding.edtNamaPengepul, getString(R.string.tidak_boleh_kosong))
                isAllSet = kontakPengepul.isEmpty(binding.edtKontakPengepul, getString(R.string.tidak_boleh_kosong))
                isAllSet = alamatPengepul.isEmpty(binding.edtAlamatPengepul, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(
                        Utils.PENGEPUL_ID,
                        Utils.PENGEPUL, getCurrentDate() + " WIB")
                    val distributorId = traceableGoodHelper.insertPengepul(Utils.PENGEPUL_ID, namaPengepul, alamatPengepul, kontakPengepul, getCurrentDate() + " WIB")

                    if (distributorId > 0) {
                        Toast.makeText(
                            this@TambahPengepulActivity,
                            "Berhasil Tambah Data ${Utils.PENGEPUL}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahPengepulActivity,
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