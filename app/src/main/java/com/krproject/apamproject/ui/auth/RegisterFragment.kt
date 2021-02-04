package com.krproject.apamproject.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.databinding.FragmentRegisterBinding
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.base.ViewModelProviderFactory
import com.krproject.apamproject.ui.enable
import com.krproject.apamproject.ui.visible

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    lateinit var authViewModel: AuthViewModel
    var debitur = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val debiturItems = resources.getStringArray(R.array.pilih_debitur)

        val debiturAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu, debiturItems)
        (binding.tieDebitur as? AutoCompleteTextView)?.setAdapter(debiturAdapter)

        binding.pbRegLoading.visible(false)
        binding.btnRegister.enable(false)

        binding.tieRegPassword.addTextChangedListener{
            val nik = binding.tieNik.text.toString().trim()
            val name = binding.tieNama.text.toString().trim()
            val nohp = binding.tieNoHp.text.toString().trim()
            val email = binding.tieRegEmail.text.toString().trim()
            binding.btnRegister.enable(
                nik.isNotEmpty() &&
                        name.isNotEmpty() &&
                        nohp.isNotEmpty() &&
                        email.isNotEmpty() &&
                        it.toString().isNotEmpty() &&
                        it.toString().length >= 7
            )
        }

        binding.btnRegister.setOnClickListener {
            val nik = binding.tieNik.text.toString().trim()
            val name = binding.tieSpinner.text.toString().trim() + binding.tieNama.text.toString().trim()
            val nohp = binding.tieNoHp.text.toString().trim()
            val email = binding.tieRegEmail.text.toString().trim()
            val password = binding.tieRegPassword.text.toString().trim()
            val bpjs = binding.tieBpjs.text.toString().trim()

//            val noBpjs = if (bpjs.isNullOrEmpty()){
//                "null"
//            }else{
//                bpjs
//            }


            if (email.isEmailValid()){
                if (debitur == ""){
                    if (binding.tilBpjs.visibility == View.GONE){
                        binding.pbRegLoading.visible(true)
                        authViewModel.registerUser(RequestBodies.RegisterBody(email, password, name, nohp, nik, bpjs))
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Pilih jenis debitur terlebih dahulu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else{
                    if (binding.tilDebitur.visibility == View.VISIBLE){
                        if (bpjs.isEmpty()){
                            Toast.makeText(
                                requireContext(),
                                "Masukkan No BPJS Terlebih dahulu",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            binding.pbRegLoading.visible(true)
                            authViewModel.registerUser(RequestBodies.RegisterBody(email, password, name, nohp, nik, bpjs))
                        }
                    }else{

                        binding.pbRegLoading.visible(true)
                        authViewModel.registerUser(RequestBodies.RegisterBody(email, password, name, nohp, nik, "null"))
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Masukkan Email yang valid", Toast.LENGTH_SHORT)
                    .show()
            }

//            findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
        }

        authViewModel.registerResponse.observe(viewLifecycleOwner, Observer { event->
            event.getContentIfNotHandled()?.let { response ->
                when(response){
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            Toast.makeText(context, "Register Berhasil, Silahkan Login", Toast.LENGTH_SHORT).show()
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

        binding.tieDebitur.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 2){
                    binding.tilBpjs.visibility = View.VISIBLE
                    debitur = binding.tieDebitur.text.toString()
                }else{
                    binding.tieBpjs.setText("")
                    debitur = ""
                    binding.tilBpjs.visibility = View.GONE
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
        }

        //Define Array View
        val dropdownMenu = listOf(
            "Tn",
            "Ny",
            "Nn",
            "An",
            "By"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_menu,
            dropdownMenu
        )

        (binding.tieSpinner as? AutoCompleteTextView)?.setAdapter(adapter)

    }

    private fun hideProgressBar() {
        binding.pbRegLoading.visibility = View.GONE
    }

    private fun showProgressLoading() {
        binding.pbRegLoading.visibility = View.VISIBLE
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
}