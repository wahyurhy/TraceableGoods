package com.wahyurhy.traceablegoods.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.BottomSheetFilterTransaksiBinding

class MyDialogFilterTransaksiFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFilterTransaksiBinding

    private var filterCallback: FilterCallback? = null

    override fun getTheme() = R.style.NoBackgroundDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomSheetFilterTransaksiBinding.inflate(inflater, container, false)
        binding.root.setBackgroundResource(R.drawable.rounded_dialog)

        when {
            Prefs.filterSemua -> {
                binding.apply {
                    rbSemua.isChecked = true
                    rbSudahSelesai.isChecked = false
                    rbBelumSelesai.isChecked = false
                }
            }
            Prefs.filterSudahSelesai -> {
                binding.apply {
                    rbSemua.isChecked = false
                    rbSudahSelesai.isChecked = true
                    rbBelumSelesai.isChecked = false
                }
            }
            Prefs.filterBelumSelesai -> {
                binding.apply {
                    rbSemua.isChecked = false
                    rbSudahSelesai.isChecked = false
                    rbBelumSelesai.isChecked = true
                }
            }
        }

        binding.closeDialog.setOnClickListener {
            dismiss()
        }

        binding.rbSemua.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                Prefs.apply {
                    filterSemua = true
                    filterSudahSelesai = false
                    filterBelumSelesai = false
                }

                val filterSemua = true
                val filterSudahSelesai = false
                val filterBelumSelesai = false

                filterCallback?.onFilterChanged(filterSemua, filterSudahSelesai, filterBelumSelesai)
            }
        }

        binding.rbSudahSelesai.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                Prefs.apply {
                    filterSemua = false
                    filterSudahSelesai = true
                    filterBelumSelesai = false
                }

                val filterSemua = false
                val filterSudahSelesai = true
                val filterBelumSelesai = false

                filterCallback?.onFilterChanged(filterSemua, filterSudahSelesai, filterBelumSelesai)
            }
        }

        binding.rbBelumSelesai.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                Prefs.apply {
                    filterSemua = false
                    filterSudahSelesai = false
                    filterBelumSelesai = true
                }

                val filterSemua = false
                val filterSudahSelesai = false
                val filterBelumSelesai = true

                filterCallback?.onFilterChanged(filterSemua, filterSudahSelesai, filterBelumSelesai)
            }
        }

        return binding.root
    }

    fun setFilterCallback(callback: FilterCallback) {
        filterCallback = callback
    }

    interface FilterCallback {
        fun onFilterChanged(filterSemua: Boolean, filterSudahSelesai: Boolean, filterBelumSelesai: Boolean)
    }

}