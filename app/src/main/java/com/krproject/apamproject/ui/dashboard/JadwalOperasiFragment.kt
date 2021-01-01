package com.krproject.apamproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.data.repository.AuthRepository
import com.krproject.apamproject.databinding.FragmentJadwalOperasiBinding
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.base.BaseFragment

class JadwalOperasiFragment : BaseFragment<AuthViewModel, FragmentJadwalOperasiBinding,
        AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_jadwalOperasiFragment_to_berandaFragment3)
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentJadwalOperasiBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}