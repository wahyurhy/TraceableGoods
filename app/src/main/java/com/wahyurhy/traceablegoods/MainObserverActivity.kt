package com.wahyurhy.traceablegoods

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wahyurhy.traceablegoods.databinding.ActivityMainObserverBinding
import com.wahyurhy.traceablegoods.ui.fragment.TentangAplikasiFragment
import com.wahyurhy.traceablegoods.ui.fragment.TransaksiFragment
import com.wahyurhy.traceablegoods.utils.Utils

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

    private fun initClickListener() {}

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