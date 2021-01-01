package com.krproject.apamproject.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.AuthApi
import com.krproject.apamproject.data.repository.AuthRepository
import com.krproject.apamproject.databinding.FragmentRegisterBinding
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.enable
import com.krproject.apamproject.ui.visible

class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

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
                        it.toString().length < 8
            )
        }

        binding.btnRegister.setOnClickListener {
            val nik = binding.tieNik.text.toString().trim()
            val name = binding.tieNama.text.toString().trim()
            val nohp = binding.tieNoHp.text.toString().trim()
            val email = binding.tieRegEmail.text.toString().trim()
            val password = binding.tieRegPassword.text.toString().trim()

            binding.pbRegLoading.visible(true)
            findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()=
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}