package com.krproject.apamproject.ui.dashboard

import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.databinding.FragmentJadwalDokterBinding
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.auth.LoginFragmentDirections
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.base.ViewModelProviderFactory
import com.krproject.apamproject.ui.jadwal_dokter.JadwalDokterAdapter
import com.krproject.apamproject.ui.jadwal_dokter.JadwalDokterViewModel
import com.krproject.apamproject.util.SharedPreferenceHelper

class JadwalDokterFragment : BaseFragment<FragmentJadwalDokterBinding>() {

    lateinit var jadwalDokterViewModel: JadwalDokterViewModel
    private lateinit var jadwalDokterAdapter: JadwalDokterAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        jadwalDokterAdapter = JadwalDokterAdapter()
        getJadwalDokter("")

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