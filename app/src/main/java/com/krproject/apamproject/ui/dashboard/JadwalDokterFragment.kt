package com.krproject.apamproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.databinding.FragmentJadwalDokterBinding
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.base.BaseFragment

class JadwalDokterFragment : BaseFragment<FragmentJadwalDokterBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_jadwalDokterFragment_to_berandaFragment3)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentJadwalDokterBinding.inflate(inflater, container, false)
}