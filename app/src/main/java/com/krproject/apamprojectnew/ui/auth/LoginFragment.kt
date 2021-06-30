package com.krproject.apamprojectnew.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.data.network.RemoteDataSource
import com.krproject.apamprojectnew.data.network.RequestBodies
import com.krproject.apamprojectnew.data.network.Resource
import com.krproject.apamprojectnew.databinding.FragmentLoginBinding
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.ui.base.ViewModelProviderFactory
import com.krproject.apamprojectnew.ui.enable
import com.krproject.apamprojectnew.ui.visible
import com.krproject.apamprojectnew.util.SharedPreferenceHelper


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    lateinit var authViewModel: AuthViewModel
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

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
                            sharedPreferenceHelper.setToken(loginResponse.body.apiToken?:"")
                            sharedPreferenceHelper.setNoRm(loginResponse.body.no_rm_pas?:"")
                            sharedPreferenceHelper.setEmail(binding.tieEmail.text.toString().trim())
                            sharedPreferenceHelper.setNama(loginResponse.body.nama_pas?:"")
                            sharedPreferenceHelper.setNama(loginResponse.body.nama_pas?:"")

                            showDialogSuccess(loginResponse.body.no_rm_pas?:"")
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
                        it.toString().length > 7
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
           requireActivity().onBackPressed()
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
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
    }

    private fun showDialogSuccess(noRm:String) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_success_login,null)
        val textDesc = view.findViewById<TextView>(R.id.desc)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val alertDialog = builder.create()

        textDesc.text = "RM: $noRm \n Berhasil Login"

        val buttonAllow = view.findViewById<Button>(R.id.btnAllow)

        buttonAllow.setOnClickListener {
            alertDialog.dismiss()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToBerandaFragment3())
        }

        alertDialog.show()

    }

}

