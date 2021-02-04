package com.krproject.apamproject.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.krproject.apamproject.R
import com.krproject.apamproject.data.network.Resource
import com.krproject.apamproject.databinding.FragmentBerandaBinding
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.ui.base.ViewModelProviderFactory
import com.krproject.apamproject.ui.tiket.ListTiketViewModel
import com.krproject.apamproject.ui.tiket.LkisTiketAdapter
import com.krproject.apamproject.util.SharedPreferenceHelper
import com.synnapps.carouselview.ImageListener

class BerandaFragment : BaseFragment<FragmentBerandaBinding>() {

    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    lateinit var tiketViewModel: ListTiketViewModel
    lateinit var tiketAdapter: LkisTiketAdapter

    private val imageList = intArrayOf(
        R.drawable.car_covid19,
        R.drawable.car_wash,
        R.drawable.car_contact
    )

    private val imageListListener = ImageListener {
            position, imageView -> imageView.setImageResource(imageList[position])
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())

        binding.daftar.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_poliklinkFormFragment)
        }

        binding.btnJadwalOperasi.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_jadwalOperasiFragment)
        }

        binding.jadwalDokter.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_jadwalDokterFragment)
        }

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_welcomeFragment)
        }

        binding.tvHistory.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment3_to_historyFragment)
        }

        binding.imageSlider.setImageListener(imageListListener)
        binding.imageSlider.pageCount = imageList.size

        binding.nama.text = sharedPreferenceHelper.getEmail()

        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Apakah Anda yakin ingin Logout?")
            builder.setPositiveButton("Ya"
            ) { dialog, which ->
                dialog?.dismiss()
                logout()
            }

            .setNegativeButton("Tidak"
            ) { dialog, which -> dialog?.dismiss() }

                .show()

        }

        getJadwalDokter(sharedPreferenceHelper.getNoRm()?:"")

    }

    private fun getJadwalDokter(noRm: String) {
        tiketAdapter.clearJadwalDokter()
        tiketViewModel.daftarTiket(noRm)
        tiketViewModel.jadwalResponse.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { loginResponse ->
//                            Toast.makeText(
//                                requireContext(),
//                                "Berhasil get ${loginResponse.body.data.size}",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            tiketAdapter.setJadwalDokter(loginResponse.body.data)

                            tiketAdapter.mOnItemClickListener = object : LkisTiketAdapter.OnItemClickListener{
                                override fun onItemClick() {
                                    val bundle = bundleOf("responseTiket" to loginResponse)
//                                    navController.navigate(R.id.action_specifyAmountFragment_to_confirmationFragment2, bundle)
                                    findNavController().navigate(R.id.action_berandaFragment3_to_historyFragment, bundle)
                                }

                            }

                            with(binding.rvTiket) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = tiketAdapter
                                visibility = View.VISIBLE
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
//                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                            println("Error $message")
                        }

                    }

                    is Resource.Loading -> {

                    }
                }
            }
        })
    }

    private fun init(){
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(requireActivity().application, repository)
        tiketViewModel = ViewModelProvider(this, factory).get(ListTiketViewModel::class.java)
        tiketAdapter = LkisTiketAdapter(requireActivity())
    }

    private fun logout(){
        sharedPreferenceHelper.clearAllDataShared()
        findNavController().navigate(R.id.action_berandaFragment3_to_welcomeFragment)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBerandaBinding.inflate(inflater, container, false)
}