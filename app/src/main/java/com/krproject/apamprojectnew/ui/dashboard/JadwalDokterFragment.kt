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
import com.krproject.apamprojectnew.databinding.FragmentJadwalDokterBinding
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.ui.base.ViewModelProviderFactory
import com.krproject.apamprojectnew.ui.jadwal_dokter.JadwalDokterAdapter
import com.krproject.apamprojectnew.ui.jadwal_dokter.JadwalDokterViewModel

class JadwalDokterFragment : BaseFragment<FragmentJadwalDokterBinding>() {

    lateinit var jadwalDokterViewModel: JadwalDokterViewModel
    private lateinit var jadwalDokterAdapter: JadwalDokterAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        jadwalDokterAdapter = JadwalDokterAdapter()
        getJadwalDokter("")

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


        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_jadwalDokterFragment_to_berandaFragment3)
        }

        binding.cariJadwal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getJadwalDokter(query?:"")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.spinnerDokter.setOnItemClickListener { parent, view, position, id ->
            Log.d("CEKONITEMSELECTED", "onItemSelected: ${binding.spinnerDokter.text}")
            if (position == 0){
                jadwalDokterAdapter.filterBaseOnDokterName("")
            }else{
                jadwalDokterAdapter.filterBaseOnDokterName(binding.spinnerDokter.text.toString())
            }

        }

        binding.spinnerPoliklinik.setOnItemClickListener { parent, view, position, id ->
            Log.d("CEKONITEMSELECTED", "onItemSelected: ${binding.spinnerPoliklinik.text}")
            if (position == 0){
                jadwalDokterAdapter.filterBaseOnNamaKlinik("")
            }else{
                jadwalDokterAdapter.filterBaseOnNamaKlinik(binding.spinnerPoliklinik.text.toString())
            }

        }

        binding.spinnerHari.setOnItemClickListener { parent, view, position, id ->
            if (position == 0){
                jadwalDokterAdapter.sortByDayAsc()
            }else{
                jadwalDokterAdapter.sortByDayDesc()
            }
        }

    }



    private fun getJadwalDokter(search: String) {
        jadwalDokterAdapter.clearJadwalDokter()
        jadwalDokterViewModel.jadwalDokter(search)
        jadwalDokterViewModel.jadwalResponse.observe(viewLifecycleOwner, Observer { event ->
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
                            jadwalDokterAdapter.setJadwalDokter(loginResponse.body.data)

                            with(binding.rvJadwalDokter) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = jadwalDokterAdapter
                                visibility = View.VISIBLE
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
                                it.nm_instalasi
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

    private fun showProgressLoading() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbLoading.visibility = View.GONE
    }

    private fun init() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        jadwalDokterViewModel = ViewModelProvider(this, factory).get(JadwalDokterViewModel::class.java)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentJadwalDokterBinding.inflate(inflater, container, false)


}