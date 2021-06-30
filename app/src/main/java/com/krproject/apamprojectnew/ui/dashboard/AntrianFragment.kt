package com.krproject.apamprojectnew.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.databinding.FragmentAntrianBinding
import com.krproject.apamprojectnew.ui.base.BaseFragment

class AntrianFragment : BaseFragment<FragmentAntrianBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_antrianFragment2_to_berandaFragment3)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAntrianBinding.inflate(inflater, container, false)
}