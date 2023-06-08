package com.wahyurhy.traceablegoods

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.databinding.ActivityMainBinding
import com.wahyurhy.traceablegoods.ui.fragment.MasterDataFragment
import com.wahyurhy.traceablegoods.ui.fragment.ProfileFragment
import com.wahyurhy.traceablegoods.ui.fragment.TransaksiFragment
import com.wahyurhy.traceablegoods.utils.Utils

class MainActivity : AppCompatActivity(), MasterDataFragment.ScrollListener,
    TransaksiFragment.ScrollListener {

    private lateinit var binding: ActivityMainBinding
    private var isMasterData = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fitStatusBar()
        initTabs()
        initClickListener()
    }

    private fun initClickListener() {
        binding.fbTambahData.setOnClickListener {
            Toast.makeText(this, "Tambah Data", Toast.LENGTH_SHORT).show()
        }
        binding.fbTambahTransaksi.setOnClickListener {
            Toast.makeText(this, "Tambah Transaksi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTabs() {
        val masterDataFragment = MasterDataFragment()
        masterDataFragment.setScrollListener(this)

        val transaksiFragment = TransaksiFragment()
        transaksiFragment.setScrollListener(this)
        val profileFragment = ProfileFragment()
        makeCurrentFragment(masterDataFragment)
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.master_data_fragment -> {
                    makeCurrentFragment(masterDataFragment)
                    it.isChecked = true
                    it.setIcon(R.drawable.ic_master_data_state)
                    setVisibilityFloatingActionButton(it.itemId)
                    false
                }
                R.id.transaksi_fragment -> {
                    makeCurrentFragment(transaksiFragment)
                    it.isChecked = true
                    it.setIcon(R.drawable.ic_transaksi_state)
                    setVisibilityFloatingActionButton(it.itemId)
                    false
                }
                R.id.profile_fragment -> {
                    makeCurrentFragment(profileFragment)
                    it.isChecked = true
                    it.setIcon(R.drawable.ic_profile_state)
                    setVisibilityFloatingActionButton(it.itemId)
                    false
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun setVisibilityFloatingActionButton(view: Int) {
        when (view) {
            R.id.master_data_fragment -> {
                isMasterData = true
                binding.fbTambahData.visibility = View.VISIBLE
                binding.fbTambahTransaksi.visibility = View.GONE
            }
            R.id.transaksi_fragment -> {
                isMasterData = false
                binding.fbTambahData.visibility = View.GONE
                binding.fbTambahTransaksi.visibility = View.VISIBLE
            }
            R.id.profile_fragment -> {
                isMasterData = false
                binding.fbTambahData.visibility = View.GONE
                binding.fbTambahTransaksi.visibility = View.GONE
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

    override fun onScrollChanged(scrollY: Int) {
        if (isMasterData) {
            if (scrollY > 0 && binding.fbTambahData.isShown) {
                binding.fbTambahData.hide()
            } else if (scrollY == 0 && !binding.fbTambahData.isShown) {
                binding.fbTambahData.show()
            }
        }
    }

    override fun onScrollChanged(scrollY: Int, recyclerView: RecyclerView) {
        if (!isMasterData) {
            if (scrollY > 10 && binding.fbTambahTransaksi.isShown) {
                binding.fbTambahTransaksi.hide()
            }

            if (scrollY < -10 && !binding.fbTambahTransaksi.isShown) {
                binding.fbTambahTransaksi.show()
            }

            if (!recyclerView.canScrollVertically(-1)) {
                binding.fbTambahTransaksi.show()
            }
        }
    }
}