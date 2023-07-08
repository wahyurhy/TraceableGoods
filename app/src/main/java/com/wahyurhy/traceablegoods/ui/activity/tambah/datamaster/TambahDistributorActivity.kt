package com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.ActivityTambahDataDistributorBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.DISTRIBUTOR
import com.wahyurhy.traceablegoods.utils.Utils.DISTRIBUTOR_ID
import java.text.SimpleDateFormat
import java.util.*

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
        initClickListener()
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

                namaDistributor.showErrorIfEmpty(binding.edtNamaDistributor, getString(R.string.tidak_boleh_kosong))
                kontakDistributor.showErrorIfEmpty(binding.edtKontakDistributor, getString(R.string.tidak_boleh_kosong))
                alamatDistributor.showErrorIfEmpty(binding.edtAlamatDistributor, getString(R.string.tidak_boleh_kosong))

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

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy - HH:mm", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
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

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}