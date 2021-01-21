package com.krproject.apamproject.ui.auth

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.RemoteDataSource
import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.databinding.FragmentLoginBinding
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.base.ViewModelProviderFactory
import com.krproject.apamproject.ui.enable
import com.krproject.apamproject.ui.visible


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    lateinit var authViewModel: AuthViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        binding.pbLoading.visible(false)
        binding.btnLogin.enable(false)

        authViewModel.loginResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { loginResponse ->
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBerandaFragment3())
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

        binding.tiePassword.addTextChangedListener {
            val email = binding.tieEmail.text.toString().trim()
            binding.btnLogin.enable(
                email.isNotEmpty() &&
                        it.toString().isNotEmpty() &&
                        it.toString().length >= 7
            )
        }

        binding.btnLogin.setOnClickListener {
            val remoteData = RemoteDataSource()
            val email = binding.tieEmail.text.toString().trim()
            val password = binding.tiePassword.text.toString().trim()
            binding.pbLoading.visible(true)
//            findNavController().navigate(R.id.action_loginFragment_to_berandaFragment3)
            authViewModel.loginUser(RequestBodies.LoginBody(email, password))
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
        }
    }

    private fun hideProgressBar() {
        binding.pbLoading.visible(false)
    }

    private fun showProgressLoading() {
        binding.pbLoading.visible(true)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    private fun init() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
    }

}

