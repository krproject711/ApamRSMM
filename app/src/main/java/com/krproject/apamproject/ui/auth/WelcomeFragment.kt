package com.krproject.apamproject.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.databinding.FragmentWelcomeBinding
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.util.SharedPreferenceHelper

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())

        if (!sharedPreferenceHelper.getToken().isNullOrEmpty()){
            findNavController().navigate(R.id.action_welcomeFragment_to_berandaFragment)
        }

        binding.masukButton.setOnClickListener{
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        binding.daftarButton.setOnClickListener{
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWelcomeBinding.inflate(inflater, container, false)
}