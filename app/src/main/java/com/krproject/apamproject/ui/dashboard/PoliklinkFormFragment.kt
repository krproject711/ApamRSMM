package com.krproject.apamproject.ui.dashboard

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.response.dokter.DataGetDokter
import com.krproject.apamproject.data.response.poli.Data
import com.krproject.apamproject.databinding.FragmentPoliklinkFormBinding
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.base.ViewModelProviderFactory
import com.krproject.apamproject.ui.dokter.ListDokterViewModel
import com.krproject.apamproject.ui.poli.ListPoliViewModel
import com.krproject.apamproject.ui.reservasi.ReservasiViewModel
import com.krproject.apamproject.util.SharedPreferenceHelper
import kotlinx.android.synthetic.main.fragment_beranda.*
import kotlinx.android.synthetic.main.fragment_poliklink_form.*
import java.util.*

class PoliklinkFormFragment : BaseFragment<FragmentPoliklinkFormBinding>() {

    lateinit var poliViewModel: ListPoliViewModel
    lateinit var dokterViewModel: ListDokterViewModel
    lateinit var reservasiViewModel: ReservasiViewModel
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    lateinit var listPoli: List<Data>
    lateinit var listDokter: List<DataGetDokter>
    var namaPoliklinik = ""
    var idPoliklinik = 0
    var namaDokter = ""
    var idDokter = 0
    var jenisTransaksi = ""
    var tanggal = ""


    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        observeRegisterForm()

        val poliItems = resources.getStringArray(R.array.pilih_poliklinik)
        val datangItems = resources.getStringArray(R.array.cara_datang)
        val debiturItems = resources.getStringArray(R.array.pilih_debitur)



        val datangAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, datangItems)
        (binding.tieSpinnerCaraDtg as? AutoCompleteTextView)?.setAdapter(datangAdapter)

        val debiturAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, debiturItems)
        (binding.tieSpinnerDebitur as? AutoCompleteTextView)?.setAdapter(debiturAdapter)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_poliklinkFormFragment_to_berandaFragment3)
        }

        binding.tieFormTanggal.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                binding.tieFormTanggal.setText("$year-${String.format("%02d", (monthOfYear + 1))}-" +
                        String.format("%02d", (dayOfMonth))
                )
                tanggal = "$year-${String.format("%02d", (monthOfYear + 1))}-" +
                        "${String.format("%02d", (dayOfMonth))} 07:00:00"

            }, year, month, day)

            dpd.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd.datePicker.maxDate = System.currentTimeMillis() + 259200000

            dpd.show()
        }

        binding.tieSpinnerPoliklinik.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listPoli.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "List data Poliklinik gagal di ambil, silahkan refresh",
                        Toast.LENGTH_SHORT).show()
                }else{
                    if (position ==0){
                        namaPoliklinik = ""
                        idPoliklinik = 0
                    }else{
                        namaPoliklinik = binding.tieSpinnerPoliklinik.text.toString()
                        idPoliklinik = listPoli.filter {
                            it.nm_instalasi == binding.tieSpinnerPoliklinik.text.toString()
                        }[0].id_ins.toInt()
                    }
                }
            }
        }

        binding.tieSpinnerDebitur.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                jenisTransaksi = if (position == 0){
                    ""
                }else{
                    binding.tieSpinnerDebitur.text.toString()
                }
            }

        }

        binding.tieSpinnerDokter.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listPoli.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "List data Dokter gagal di ambil, silahkan refresh",
                        Toast.LENGTH_SHORT).show()
                }else{
                    if (position ==0){
                        namaDokter = ""
                        idDokter = 0
                    }else{
                        namaDokter = binding.tieSpinnerDokter.text.toString()
                        idDokter = listPoli.filter {
                            it.nmlengkap == binding.tieSpinnerDokter.text.toString()
                        }[0].id_dok.toInt()
                    }
                }
            }
        }

        binding.btnDaftarPoliklinik.setOnClickListener {
            val name = binding.tieNamaPasien.text.toString()
            when {
                name.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Masukkan Nama Terlebih Dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.tieNamaPasien.setError("Required")
                }
                idPoliklinik == 0 -> {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Poliklinik terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                tanggal.isEmpty() ->{
                    Toast.makeText(requireContext(), "Pilih Tanggal terlebih dahulu", Toast.LENGTH_SHORT)
                        .show()
                }
                idDokter == 0 -> {
                    Toast.makeText(requireContext(), "Pilih Dokter terlebih dahulu", Toast.LENGTH_SHORT)
                        .show()
                }
                jenisTransaksi.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Jenis transaksi terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Log.d("CEKUSER", "" +
                            "onActivityCreated: " +
                            "noRM: ${sharedPreferenceHelper.getNoRm()}" +
                            "Nama: $name," +
                            "nameDokter: $idDokter," +
                            "idPoli: $idPoliklinik," +
                            "spinnerDebitur: ${binding.tieSpinnerDebitur.text}," +
                            "email: ${sharedPreferenceHelper.getEmail()}" +
                            "tanggal: $tanggal")
                    reservasiViewModel.registerUser(
                        sharedPreferenceHelper.getNoRm()?:"",
                        name, idDokter.toString(),
                        idPoliklinik.toString(),
                        binding.tieSpinnerDebitur.text.toString(),
                        sharedPreferenceHelper.getEmail()?:"",
                        tanggal
                    )
                }
            }
        }

        getListPoliandSetToSpinner()
//        getListDokterandSetToSpinner()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPoliklinkFormBinding.inflate(inflater, container, false)

    private fun init() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        poliViewModel = ViewModelProvider(this, factory).get(ListPoliViewModel::class.java)
        dokterViewModel = ViewModelProvider(this, factory).get(ListDokterViewModel::class.java)
        reservasiViewModel = ViewModelProvider(this, factory).get(ReservasiViewModel::class.java)
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
    }

    private fun getListPoliandSetToSpinner(){

        poliViewModel.jadwalOperasi()
        poliViewModel.jadwalResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->

                            listPoli  = loginResponse.body.data
                            //Define Array View
                            val dropdownMenuDokter = loginResponse.body.data.map {
                                it.nm_instalasi
                            }.distinct()

                            val arrayDokter = arrayListOf<String>()
                            arrayDokter.add("Pilih Poliklinik")
                            arrayDokter.addAll(dropdownMenuDokter)


                            val adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_menu,
                                arrayDokter
                            )


                            (binding.tieSpinnerPoliklinik as? AutoCompleteTextView)?.setAdapter(adapter)

                            val dropdownMenuPoliklinik = loginResponse.body.data.map {
                                it.nmlengkap
                            }.distinct()

                            val arrayPoliklinik = arrayListOf<String>()
                            arrayPoliklinik.add("Pilih Poliklinik")
                            arrayPoliklinik.addAll(dropdownMenuPoliklinik)


                            val adapterPoliklinik = ArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_menu,
                                arrayPoliklinik
                            )

                            (binding.tieSpinnerDokter as? AutoCompleteTextView)?.setAdapter(adapterPoliklinik)


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

//    private fun getListDokterandSetToSpinner(){
//
//        dokterViewModel.daftarDokter()
//        dokterViewModel.jadwalResponse.observe(viewLifecycleOwner, Observer { event ->
//            event.getContentIfNotHandled()?.let { response ->
//                when (response) {
//                    is Resource.Success -> {
//                        hideProgressBar()
//                        response.data?.let { loginResponse ->
//
//                            listDokter  = loginResponse.body.data
//                            //Define Array View
//                            val dropdownMenuDokter = loginResponse.body.data.map {
//                                it.nmlengkap
//                            }.distinct()
//
//                            val arrayDokter = arrayListOf<String>()
//                            arrayDokter.add("Pilih Dokter")
//                            arrayDokter.addAll(dropdownMenuDokter)
//
//
//                            val adapter = ArrayAdapter(
//                                requireContext(),
//                                R.layout.dropdown_menu,
//                                arrayDokter
//                            )
//
//
//                            (binding.tieSpinnerDokter as? AutoCompleteTextView)?.setAdapter(adapter)
//
//
//                        }
//                    }
//
//                    is Resource.Error -> {
//                        hideProgressBar()
//                        response.message?.let { message ->
//                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                            println("Error $message")
//                        }
//
//                    }
//
//                    is Resource.Loading -> {
//                        showProgressLoading()
//                    }
//                }
//            }
//        })
//
//
//
//    }

    private fun observeRegisterForm(){
        reservasiViewModel.reservasiResponse.observe(viewLifecycleOwner, Observer { event->
            event.getContentIfNotHandled()?.let { response ->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            findNavController().navigate(R.id.action_poliklinkFormFragment_to_berandaFragment3)
                            Toast.makeText(context, "Reservasi Berhasil, Silahkan Login", Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            println("Error $message" )
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
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}

