package com.wahyurhy.traceablegoods.ui.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        binding.btnLogout.setOnClickListener {
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
    }

    private fun initProfile() {
        binding.apply {
            inisialNama.text = Prefs.namaAdmin[0].toString()
            tvName.text = Prefs.namaAdmin
            tvEmail.text = Prefs.emailAdmin
        }
    }

}