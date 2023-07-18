package com.wahyurhy.traceablegoods.ui.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.wahyurhy.traceablegoods.MainActivity
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiAdapter
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiPenerimaAdapter
import com.wahyurhy.traceablegoods.adapter.AlurDistribusiProdusenAdapter
import com.wahyurhy.traceablegoods.databinding.ActivityTahapAlurDistribusiBinding
import com.wahyurhy.traceablegoods.db.TraceableGoodHelper
import com.wahyurhy.traceablegoods.model.AlurDistribusi
import com.wahyurhy.traceablegoods.utils.MappingHelper
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_AFTER_TAHAP_PENERIMA
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_BATCH_ID
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_JENIS_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_NAMA_PRODUK_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_STATUS_TRANSAKSI
import com.wahyurhy.traceablegoods.utils.Utils.getCurrentDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class TahapAlurDistribusiActivity : AppCompatActivity() {

    private lateinit var adapterProdusenAdapter: AlurDistribusiProdusenAdapter
    private lateinit var adapterDistributorAdapter: AlurDistribusiAdapter
    private lateinit var adapterPenerimaAdapter: AlurDistribusiPenerimaAdapter
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var binding: ActivityTahapAlurDistribusiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTahapAlurDistribusiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val batchId = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
        val afterTahapPenerima = intent.getStringExtra(EXTRA_AFTER_TAHAP_PENERIMA) ?: ""
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

        if (afterTahapPenerima != "") {
            Handler(Looper.getMainLooper()).postDelayed({
                setPrintStruk()
            }, 2000)
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

        binding.btnPrint.setOnClickListener {
            setPrintStruk()
        }
    }

    private fun setPrintStruk() {
        val batchId = intent.getStringExtra(EXTRA_BATCH_ID) ?: ""
        val namaProduk = intent.getStringExtra(EXTRA_NAMA_PRODUK_TRANSAKSI) ?: ""

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_DENIED
        ) {
            if (Build.VERSION.SDK_INT >= 31) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    PERMISSION_BLUETOOTH
                )
            }
        }

        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager

        if (Build.VERSION.SDK_INT >= 31) {
            bluetoothAdapter = bluetoothManager.adapter
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        }

        if (bluetoothAdapter.isEnabled) {
            val connection = BluetoothPrintersConnections.selectFirstPaired()
            if (connection != null) {
                printStruk(connection, namaProduk, batchId)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.printer_belum_terkoneksi),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun printStruk(
        connection: BluetoothConnection?,
        namaProduk: String,
        batchId: String
    ) {
        val printer = EscPosPrinter(connection, 203, 48f, 32)

        val text = "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
            printer,
            this.applicationContext.resources.getDrawableForDensity(
                R.drawable.logo_struk,
                DisplayMetrics.DENSITY_LOW,
                theme
            )
        ) + "</img>\n" +
                "[C]---------------------------\n" +
                "[C]<font size='big-2'>${getString(R.string.alur_distribusi_struk)}</font>\n" +
                "[C]<font size='big'>${namaProduk.uppercase()}</font>\n" +
                "[C]${getCurrentDate()} WIB\n" +
                "[L]\n" +
                "[C]<qrcode size='29'>$batchId</qrcode>\n" +
                "[C]<b>$batchId</b>\n" +
                "[L]\n" +
                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
            printer,
            this.applicationContext.resources.getDrawableForDensity(
                R.drawable.border_bottom,
                DisplayMetrics.DENSITY_LOW,
                theme
            )
        ) + "</img>\n" +
                "[L]"

        Toast.makeText(this, getString(R.string.sedang_mencetak_batch_id), Toast.LENGTH_SHORT)
            .show()
        printer.printFormattedText(text)
        Toast.makeText(this, getString(R.string.berhasil_mencetak_batch_id), Toast.LENGTH_SHORT)
            .show()
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
                } else {
                    adapterProdusenAdapter.mAlurDistribusi = ArrayList()
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
                } else {
                    adapterDistributorAdapter.mAlurDistribusi = ArrayList()
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
                } else {
                    adapterPenerimaAdapter.mAlurDistribusi = ArrayList()
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

    companion object {
        const val PERMISSION_BLUETOOTH = 100
    }

}