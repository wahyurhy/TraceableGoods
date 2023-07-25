package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataGudangBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty

class TambahGudangActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private lateinit var binding: ActivityTambahDataGudangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataGudangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initEditText()
        initClickListener()
    }

    private fun initEditText() {
        val gudang = intent.getStringExtra(Utils.EXTRA_NAMA_GUDANG) ?: ""
        binding.edtNamaGudang.setText(gudang)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val namaGudang = edtNamaGudang.text.toString().trim()
                val kontakGudang = edtKontakGudang.text.toString().trim()
                val alamatGudang = edtAlamatGudang.text.toString().trim()

                isAllSet = namaGudang.isEmpty(binding.edtNamaGudang, getString(R.string.tidak_boleh_kosong))
                isAllSet = kontakGudang.isEmpty(binding.edtKontakGudang, getString(R.string.tidak_boleh_kosong))
                isAllSet = alamatGudang.isEmpty(binding.edtAlamatGudang, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(
                        Utils.GUDANG_ID,
                        Utils.GUDANG, getCurrentDate() + " WIB")
                    val distributorId = traceableGoodHelper.insertGudang(Utils.GUDANG_ID, namaGudang, alamatGudang, kontakGudang, getCurrentDate() + " WIB")

                    if (distributorId > 0) {
                        Toast.makeText(
                            this@TambahGudangActivity,
                            "Berhasil Tambah Data ${Utils.GUDANG}",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahGudangActivity,
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