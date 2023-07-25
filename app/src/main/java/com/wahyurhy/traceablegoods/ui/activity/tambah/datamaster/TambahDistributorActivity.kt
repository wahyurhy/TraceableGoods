package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataDistributorBinding
import com.wahyurhy.traceablegoods.core.data.source.local.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.DISTRIBUTOR_ID
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import com.wahyurhy.traceablegoods.utils.Utils.isEmpty

class TambahDistributorActivity : AppCompatActivity() {

    private lateinit var traceableGoodHelper: TraceableGoodHelper
    private var isAllSet: Boolean = false

    private lateinit var binding: ActivityTambahDataDistributorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataDistributorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
        traceableGoodHelper.open()

        fitStatusBar()
        initEditText()
        initClickListener()
    }

    private fun initEditText() {
        val distributor = intent.getStringExtra(Utils.EXTRA_NAMA_DISTRIBUTOR) ?: ""
        binding.edtNamaDistributor.setText(distributor)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            binding.apply {
                val namaDistributor = edtNamaDistributor.text.toString().trim()
                val kontakDistributor = edtKontakDistributor.text.toString().trim()
                val alamatDistributor = edtAlamatDistributor.text.toString().trim()

                isAllSet = namaDistributor.isEmpty(binding.edtNamaDistributor, getString(R.string.tidak_boleh_kosong))
                isAllSet = kontakDistributor.isEmpty(binding.edtKontakDistributor, getString(R.string.tidak_boleh_kosong))
                isAllSet = alamatDistributor.isEmpty(binding.edtAlamatDistributor, getString(R.string.tidak_boleh_kosong))

                if (isAllSet) {
                    traceableGoodHelper.insertDataInfo(DISTRIBUTOR_ID, DISTRIBUTOR, getCurrentDate() + " WIB")
                    val distributorId = traceableGoodHelper.insertDistributor(DISTRIBUTOR_ID, namaDistributor, alamatDistributor, kontakDistributor, getCurrentDate() + " WIB")

                    if (distributorId > 0) {
                        Toast.makeText(
                            this@TambahDistributorActivity,
                            "Berhasil Tambah Data $DISTRIBUTOR",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@TambahDistributorActivity,
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