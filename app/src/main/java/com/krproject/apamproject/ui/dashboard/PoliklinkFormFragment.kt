package com.krproject.apamproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.databinding.FragmentPoliklinkFormBinding
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.visible

class PoliklinkFormFragment : BaseFragment<FragmentPoliklinkFormBinding>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnDaftarPoliklinik.visible(false)
        binding.tieNomorKartu.visible(false)
        binding.tieNomorRujukan.visible(false)

        val poliItems = resources.getStringArray(R.array.pilih_poliklinik)
        val datangItems = resources.getStringArray(R.array.cara_datang)
        val debiturItems = resources.getStringArray(R.array.pilih_debitur)

        val poliAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, poliItems)
        (binding.tieSpinnerPoliklinik as? AutoCompleteTextView)?.setAdapter(poliAdapter)

        val datangAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, datangItems)
        (binding.tieSpinnerCaraDtg as? AutoCompleteTextView)?.setAdapter(datangAdapter)
        binding.tieSpinnerCaraDtg.onItemClickListener = AdapterView.OnItemClickListener {
            parent,view,position,id ->
            val datangSelected = parent.getItemAtPosition(position).toString()
            if (datangSelected !== "Datang Sendiri") {
                binding.tieNomorKartu.visible(true)
            }
        }

        val debiturAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, debiturItems)
        (binding.tieSpinnerDebitur as? AutoCompleteTextView)?.setAdapter(debiturAdapter)
        binding.tieSpinnerDebitur.onItemClickListener = AdapterView.OnItemClickListener {
                parent,view,position,id ->
            val debiturSelected = parent.getItemAtPosition(position).toString()
            if (debiturSelected == "BPJS") {
                binding.tieNomorKartu.visible(true)
            }
        }

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_poliklinkFormFragment_to_berandaFragment3)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPoliklinkFormBinding.inflate(inflater, container, false)

}

