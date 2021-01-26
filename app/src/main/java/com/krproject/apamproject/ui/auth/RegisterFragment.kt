package com.krproject.apamproject.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

            binding.pbRegLoading.visible(true)
            authViewModel.registerUser(RequestBodies.RegisterBody(email, password, name, nohp, nik))
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
        binding.pbRegLoading.visible(false)
    }

    private fun showProgressLoading() {
        binding.pbRegLoading.visible(true)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)
}