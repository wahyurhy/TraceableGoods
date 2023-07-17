package com.wahyurhy.traceablegoods

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.wahyurhy.traceablegoods.databinding.ActivityMainObserverBinding
import com.wahyurhy.traceablegoods.ui.fragment.tentangaplikasi.TentangAplikasiFragment
import com.wahyurhy.traceablegoods.ui.fragment.transaksi.TransaksiFragment
import com.wahyurhy.traceablegoods.utils.CaptureAct
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.EXTRA_BATCH_ID

class MainObserverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainObserverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainObserverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
        initTabs()
        initClickListener()


    }

    private fun scanQRCode() {
        val options = ScanOptions().apply {
            setPrompt(getString(R.string.flash_on))
            setBeepEnabled(true)
            setOrientationLocked(true)
            captureActivity = CaptureAct::class.java
        }
        barLauncher.launch(options)
    }

    val barLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
        ScanContract()
    ) { result ->
        if (result.contents != null) {
            val transaksiFragment = TransaksiFragment()
            val bundle = Bundle().apply {
                putString(EXTRA_BATCH_ID, result.contents)
            }
            transaksiFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.fl_wrapper, transaksiFragment)
                .commit()
        }
    }

    private fun initClickListener() {
        binding.btnScan.setOnClickListener {
            scanQRCode()
        }
    }

    private fun initTabs() {
        val transaksiFragment = TransaksiFragment()
        val tentangAplikasiFragment = TentangAplikasiFragment()
        makeCurrentFragment(transaksiFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.transaksi_fragment -> {
                    makeCurrentFragment(transaksiFragment)
                    it.isChecked = true
                    it.setIcon(R.drawable.ic_transaksi_state)
                    false
                }
                R.id.tentang_aplikasi_fragment -> {
                    makeCurrentFragment(tentangAplikasiFragment)
                    it.isChecked = true
                    it.setIcon(R.drawable.ic_tentang_aplikasi_state)
                    false
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

    private fun fitStatusBar() {
        Utils.setSystemBarFitWindow(this)
    }
}