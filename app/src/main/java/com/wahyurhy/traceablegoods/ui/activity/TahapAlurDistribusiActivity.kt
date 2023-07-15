package com.wahyurhy.traceablegoods.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiAdapter
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiPenerimaAdapter
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiProdusenAdapter
import com.wahyurhy.traceablegoods.databinding.ActivityTahapAlurDistribusiBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.AlurDistribusi
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_BATCH_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_STATUS_TRANSAKSI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TahapAlurDistribusiActivity : AppCompatActivity() {

    private lateinit var adapterProdusenAdapter: AlurDistribusiProdusenAdapter
    private lateinit var adapterDistributorAdapter: AlurDistribusiAdapter
    private lateinit var adapterPenerimaAdapter: AlurDistribusiPenerimaAdapter

    private lateinit var binding: ActivityTahapAlurDistribusiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapAlurDistribusiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val batchId = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
        binding.batchId.text = batchId

        setAdapter()

        fitStatusBar()

        initClickListener()
        generateQRCode(batchId)

        if (savedInstanceState == null) {
            // proses ambil data
            loadDataAlurDistribusiProdusen()
            loadDataAlurDistribusiDistributor()
            loadDataAlurDistribusiPenerima()
        } else {
            val listAlurProdusen = savedInstanceState.getParcelableArrayList<AlurDistribusi>(Utils.EXTRA_ALUR_PRODUSEN)
            val listAlurDistributor = savedInstanceState.getParcelableArrayList<AlurDistribusi>(Utils.EXTRA_ALUR_DISTRIBUTOR)
            val listAlurPenerima = savedInstanceState.getParcelableArrayList<AlurDistribusi>(Utils.EXTRA_ALUR_PENERIMA)
            if (listAlurProdusen != null) {
                adapterProdusenAdapter.mAlurDistribusi = listAlurProdusen
            }
            if (listAlurDistributor != null) {
                adapterDistributorAdapter.mAlurDistribusi = listAlurDistributor
            }
            if (listAlurPenerima != null) {
                adapterPenerimaAdapter.mAlurDistribusi = listAlurPenerima
            }
        }
    }

    private fun generateQRCode(batchId: String) {
        val writer = MultiFormatWriter()
        val matrix = writer.encode(batchId, BarcodeFormat.QR_CODE, 200, 200)

        val encoder = BarcodeEncoder()
        val bitmap = encoder.createBitmap(matrix)
        binding.qrCode.setImageBitmap(bitmap)
    }

    private fun initClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter() {
        adapterProdusenAdapter = AlurDistribusiProdusenAdapter()
        binding.rvAlurProdusen.adapter = adapterProdusenAdapter

        adapterDistributorAdapter = AlurDistribusiAdapter()
        binding.rvAlurDistribusi.adapter = adapterDistributorAdapter

        adapterPenerimaAdapter = AlurDistribusiPenerimaAdapter()
        binding.rvAlurPenerima.adapter = adapterPenerimaAdapter
    }

    private fun loadDataAlurDistribusiProdusen() {
        lifecycleScope.launch {
            binding.apply {
                val traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
                traceableGoodHelper.open()

                val deferredAlurDistribusi = async(Dispatchers.IO) {
                    val batchId = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
                    val jenisProduk = intent.getStringExtra(EXTRA_JENIS_PRODUK_TRANSAKSI) ?: ""
                    val cursor = traceableGoodHelper.queryAlurDistribusiByIdAndJenisProduk(batchId, jenisProduk)
                    MappingHelper.mapCursorToArrayListAlurDistribusi(cursor)
                }

                val alurDistribusi = deferredAlurDistribusi.await()

                if (alurDistribusi.size > 0) {
                    adapterProdusenAdapter.mAlurDistribusi = alurDistribusi
                    adapterProdusenAdapter.notifyDataSetChanged()

                    adapterProdusenAdapter.setOnClickedListener(object : AlurDistribusiProdusenAdapter.OnItemClickListener {
                        override fun onItemClick(itemView: View?, position: Int) {
                            val batchId = alurDistribusi[position].batchId
                            Toast.makeText(
                                this@TahapAlurDistribusiActivity,
                                "$batchId was clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } else {
                    adapterProdusenAdapter.mAlurDistribusi = ArrayList()
                    Toast.makeText(this@TahapAlurDistribusiActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                        .show()
                }
                traceableGoodHelper.close()
            }
        }
    }

    private fun loadDataAlurDistribusiDistributor() {
        lifecycleScope.launch {
            binding.apply {
                val traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
                traceableGoodHelper.open()

                val deferredAlurDistribusi = async(Dispatchers.IO) {
                    val batchId = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
                    val cursor = traceableGoodHelper.queryAlurDistribusiByIdAndJenisProduk(batchId, "")
                    MappingHelper.mapCursorToArrayListAlurDistribusi(cursor)
                }

                val alurDistribusi = deferredAlurDistribusi.await()

                if (alurDistribusi.size > 0) {
                    adapterDistributorAdapter.mAlurDistribusi = alurDistribusi
                    adapterDistributorAdapter.notifyDataSetChanged()

                    adapterDistributorAdapter.setOnClickedListener(object : AlurDistribusiAdapter.OnItemClickListener {
                        override fun onItemClick(itemView: View?, position: Int) {
                            val batchId = alurDistribusi[position].batchId
                            Toast.makeText(
                                this@TahapAlurDistribusiActivity,
                                "$batchId was clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } else {
                    adapterDistributorAdapter.mAlurDistribusi = ArrayList()
                    Toast.makeText(this@TahapAlurDistribusiActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                        .show()
                }
                traceableGoodHelper.close()
            }
        }
    }

    private fun loadDataAlurDistribusiPenerima() {
        lifecycleScope.launch {
            binding.apply {
                val traceableGoodHelper = TraceableGoodHelper.getInstance(applicationContext)
                traceableGoodHelper.open()

                val deferredAlurDistribusi = async(Dispatchers.IO) {
                    val batchId = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
                    val status = intent.getStringExtra(EXTRA_STATUS_TRANSAKSI) ?: ""
                    val cursor = traceableGoodHelper.queryAlurDistribusiByIdAndStatus(batchId, status)
                    MappingHelper.mapCursorToArrayListAlurDistribusi(cursor)
                }

                val alurDistribusi = deferredAlurDistribusi.await()

                if (alurDistribusi.size > 0) {
                    adapterPenerimaAdapter.mAlurDistribusi = alurDistribusi
                    adapterPenerimaAdapter.notifyDataSetChanged()

                    adapterPenerimaAdapter.setOnClickedListener(object : AlurDistribusiPenerimaAdapter.OnItemClickListener {
                        override fun onItemClick(itemView: View?, position: Int) {
                            val batchId = alurDistribusi[position].batchId
                            Toast.makeText(
                                this@TahapAlurDistribusiActivity,
                                "$batchId was clicked!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                } else {
                    adapterPenerimaAdapter.mAlurDistribusi = ArrayList()
                    Toast.makeText(this@TahapAlurDistribusiActivity, "Tidak ada data saat ini", Toast.LENGTH_SHORT)
                        .show()
                }
                traceableGoodHelper.close()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(Utils.EXTRA_ALUR_PRODUSEN, adapterProdusenAdapter.mAlurDistribusi)
        outState.putParcelableArrayList(Utils.EXTRA_ALUR_DISTRIBUTOR, adapterDistributorAdapter.mAlurDistribusi)
        outState.putParcelableArrayList(Utils.EXTRA_ALUR_PENERIMA, adapterPenerimaAdapter.mAlurDistribusi)
    }


    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }

}