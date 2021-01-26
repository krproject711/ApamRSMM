package com.krproject.apamproject.ui.dashboard

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamproject.R
import com.krproject.apamproject.databinding.FragmentBerandaBinding
import com.krproject.apamproject.ui.base.BaseFragment
import com.krproject.apamproject.util.SharedPreferenceHelper
import com.synnapps.carouselview.ImageListener

class BerandaFragment : BaseFragment<FragmentBerandaBinding>() {

    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

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