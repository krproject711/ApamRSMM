package com.krproject.apamprojectnew.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.databinding.FragmentJadwalOperasiBinding
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.ui.base.ViewModelProviderFactory
import com.krproject.apamprojectnew.ui.jadwal_operasi.JadwalOperasiAdapter
import com.krproject.apamprojectnew.ui.jadwal_operasi.JadwalOperasiViewModel

class JadwalOperasiFragment : BaseFragment<FragmentJadwalOperasiBinding>() {

    lateinit var jadwalOperasiViewModel: JadwalOperasiViewModel
    private lateinit var jadwalOperasiAdapter: JadwalOperasiAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_jadwalOperasiFragment_to_berandaFragment3)
        }

        getJadwalOperasi("")

//        binding.cariJadwal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                getJadwalOperasi(query?:"")
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//
//        })

        //Define Array View
        val dropdownMenu = listOf(
            "Ascending",
            "Descending"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu,
            dropdownMenu
        )

        (binding.spinnerHari as? AutoCompleteTextView)?.setAdapter(adapter)


        binding.spinnerDokter.setOnItemClickListener { parent, view, position, id ->
            Log.d("CEKONITEMSELECTED", "onItemSelected: ${binding.spinnerDokter.text}")
            if (position == 0){
                jadwalOperasiAdapter.filterBaseOnDokterName("")
            }else{
                jadwalOperasiAdapter.filterBaseOnDokterName(binding.spinnerDokter.text.toString())
            }

        }

         binding.spinnerPoliklinik.setOnItemClickListener { parent, view, position, id ->
            Log.d("CEKONITEMSELECTED", "onItemSelected: ${binding.spinnerPoliklinik.text}")
             if (position == 0){
                 jadwalOperasiAdapter.filterBaseOnNamaKlinik("")
             }else{
                 jadwalOperasiAdapter.filterBaseOnNamaKlinik(binding.spinnerPoliklinik.text.toString())
             }

        }


        binding.spinnerHari.setOnItemClickListener { parent, view, position, id ->
            if (position == 0){
                jadwalOperasiAdapter.sortByDayAsc()
            }else{
                jadwalOperasiAdapter.sortByDayDesc()
            }
        }



    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentJadwalOperasiBinding.inflate(inflater, container, false)

    private fun getJadwalOperasi(search: String) {
        jadwalOperasiViewModel.jadwalOperasi(search)
        jadwalOperasiViewModel.jadwalResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            Toast.makeText(
                                requireContext(),
                                "Berhasil get ${loginResponse.body.data.size}",
                                Toast.LENGTH_SHORT
                            ).show()
                            jadwalOperasiAdapter.setJadwalDokter(loginResponse.body.data)

                            with(binding.rvJadwalOperasi) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = jadwalOperasiAdapter
                                visibility = android.view.View.VISIBLE
                            }

                            //Define Array View
                            val dropdownMenuDokter = loginResponse.body.data.map {
                                it.nmlengkap
                            }.distinct()

                            val arrayDokter = arrayListOf<String>()
                            arrayDokter.add("Lihat Semua")
                            arrayDokter.addAll(dropdownMenuDokter)


                            val adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_menu,
                                arrayDokter
                            )

                            (binding.spinnerDokter as? AutoCompleteTextView)?.setAdapter(adapter)

                            //Define Array View
                            val dropdownMenuPoliklinik = loginResponse.body.data.map {
                                it.nm_brg
                            }.distinct()

                            val arrayKlinik = arrayListOf<String>()
                            arrayKlinik.add("Lihat Semua")
                            arrayKlinik.addAll(dropdownMenuPoliklinik)

                            val adapter2 = ArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_menu,
                                arrayKlinik
                            )

                            (binding.spinnerPoliklinik as? AutoCompleteTextView)?.setAdapter(adapter2)


                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            println("Error $message")
                        }

                    }

                    is Resource.Loading -> {
                        showProgressLoading()
                    }
                }
            }
        })
    }

    private fun init() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        jadwalOperasiViewModel = ViewModelProvider(this, factory).get(JadwalOperasiViewModel::class.java)
        jadwalOperasiAdapter = JadwalOperasiAdapter()
    }

    private fun showProgressLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.GONE
    }
}