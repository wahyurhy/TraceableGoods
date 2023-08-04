package com.wahyurhy.traceablegoods.ui.fragment.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wahyurhy.traceablegoods.R
import com.wahyurhy.traceablegoods.databinding.FragmentProfileBinding
import com.wahyurhy.traceablegoods.ui.activity.login.LoginActivity
import com.wahyurhy.traceablegoods.utils.Prefs

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initProfile()
        initSwitchStatus()

        initClickListener()
    }

    private fun initSwitchStatus() {
        if (Prefs.autoPrint) binding.btnPrintMode.setImageResource(R.drawable.ic_switch_on)
        else binding.btnPrintMode.setImageResource(R.drawable.ic_switch_off)
    }

    private fun initClickListener() {
        binding.btnLogout.setOnClickListener {
            logout()
        }

        binding.btnPrintMode.setOnClickListener {
            val isAutoPrint = !Prefs.autoPrint
            Prefs.autoPrint = isAutoPrint
            if (isAutoPrint) {
                binding.btnPrintMode.setImageResource(R.drawable.ic_switch_on)
            } else {
                binding.btnPrintMode.setImageResource(R.drawable.ic_switch_off)
            }
        }
    }

    private fun logout() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        alertDialogBuilder.setTitle("Logout?")
        alertDialogBuilder.setMessage("Apakah Anda yakin ingin log-out?")

        alertDialogBuilder.setPositiveButton("Ya") { _, _ ->
            Prefs.apply {
                isLogin = false
                idAdmin = -1
                namaAdmin = ""
                emailAdmin = ""
            }
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
            }
        }

        alertDialogBuilder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialogBuilder.show()
    }

    private fun initProfile() {
        binding.apply {
            inisialNama.text = Prefs.namaAdmin[0].toString()
            tvName.text = Prefs.namaAdmin
            tvEmail.text = Prefs.emailAdmin
        }
    }

}