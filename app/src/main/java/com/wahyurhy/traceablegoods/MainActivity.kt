package com.wahyurhy.traceablegoods

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wahyurhy.traceablegoods.databinding.ActivityMainBinding
import com.wahyurhy.traceablegoods.ui.activity.login.LoginActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.datamaster.TambahDataMasterActivity
import com.wahyurhy.traceablegoods.ui.activity.tambah.transaksi.tahapprodusen.TahapProdusenActivity
import com.wahyurhy.traceablegoods.ui.fragment.masterdata.MasterDataFragment
import com.wahyurhy.traceablegoods.ui.fragment.profile.ProfileFragment
import com.wahyurhy.traceablegoods.ui.fragment.transaksi.TransaksiFragment
import com.wahyurhy.traceablegoods.utils.Prefs
import com.wahyurhy.traceablegoods.utils.Utils
import com.wahyurhy.traceablegoods.utils.Utils.showExitToast

class MainActivity : AppCompatActivity(), TransaksiFragment.ScrollListener,
    MasterDataFragment.ScrollListener {

    private lateinit var binding: ActivityMainBinding
    private var isMasterData = true
    private var backPressedTime: Long = 0
    private val exitToastDuration: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Prefs.isLogin) {
            Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        }

        fitStatusBar()
        initTabs()
        initClickListener()
    }

    private fun initClickListener() {
        binding.fbTambahData.setOnClickListener {
            startActivity(Intent(this, TambahDataMasterActivity::class.java))
        }
        binding.fbTambahTransaksi.setOnClickListener {
            startActivity(Intent(this, TahapProdusenActivity::class.java))
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

    override fun onScrollChanged(scrollY: Int, recyclerView: RecyclerView) {
        if (!isMasterData) {
            if (scrollY > 10 && binding.fbTambahTransaksi.isShown) {
                binding.fbTambahTransaksi.shrink()
            }

            if (scrollY < -10 && !binding.fbTambahTransaksi.isShown) {
                binding.fbTambahTransaksi.extend()
            }

            if (!recyclerView.canScrollVertically(-1)) {
                binding.fbTambahTransaksi.extend()
            }
        }
    }

    override fun onScrollChanged(scrollY: Int, oldScrollY: Int) {
        if (isMasterData) {
            // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 12 && binding.fbTambahData.isExtended) {
                binding.fbTambahData.shrink()
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 12 && !binding.fbTambahData.isExtended) {
                binding.fbTambahData.extend()
            }

            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0) {
                binding.fbTambahData.extend()
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < exitToastDuration) {
            super.onBackPressed()
        } else {
            showExitToast(this)
            backPressedTime = System.currentTimeMillis()
        }
    }
}