package com.krproject.apamproject.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.data.network.RemoteDataSource
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.data.repository.AuthRepository
import com.krproject.apamproject.databinding.FragmentLoginBinding

import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.dashboard.BerandaActivity
import com.krproject.apamproject.ui.enable
import com.krproject.apamproject.ui.startNewActivity
import com.krproject.apamproject.ui.visible

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.pbLoading.visible(false)
        binding.btnLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.pbLoading.visible(false)
            when (it) {
                is Resource.Success -> {
//                        viewModel.saveAuthToken(it.value.user.access_token)
                        requireActivity().startNewActivity(BerandaActivity::class.java)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.tiePassword.addTextChangedListener {
            val email = binding.tieEmail.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() &&
                    it.toString().isNotEmpty() &&
            it.toString().length > 7)
        }

        binding.btnLogin.setOnClickListener {
            val remoteData = RemoteDataSource()
            val email = binding.tieEmail.text.toString().trim()
            val password = binding.tiePassword.text.toString().trim()
            binding.pbLoading.visible(true)
            findNavController().navigate(R.id.action_loginFragment_to_berandaFragment3)
//            viewModel.login(email, password)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

}

