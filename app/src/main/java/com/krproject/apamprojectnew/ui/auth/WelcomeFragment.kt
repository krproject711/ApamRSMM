package com.krproject.apamprojectnew.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.databinding.FragmentWelcomeBinding
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.util.SharedPreferenceHelper

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

        binding.lupepasswordButton.setOnClickListener{
            findNavController().navigate(R.id.action_welcomeFragment_to_forgetPasswordFragment)
        }


    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentWelcomeBinding.inflate(inflater, container, false)
}