package com.krproject.apamprojectnew.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.databinding.FragmentHistoryBinding
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.ui.tiket.ResponseTiket

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    lateinit var responseTiket: ResponseTiket

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_berandaFragment3)
        }

        responseTiket = requireArguments().getParcelable("responseTiket")!!

        val now = responseTiket.body.now

        binding.txtTanggal.text = responseTiket.body.data[0].tgl_reg_utama
        binding.txtPoliklinik.text = responseTiket.body.data[0].nm_instalasi
        binding.txtMetode.text = responseTiket.body.data[0].debitur_reg_utama
        binding.txtNoAntrian.text = responseTiket.body.data[0].no_urut_pas

        if (now !=null){
//            binding.txtAntrianSaatIni.text = now[0].no_urut_pas
//            binding.tvDokter.text = now[0].nmlengkap
//            binding.tvPoliklinik.text = now[0].nm_instalasi
//            binding.debitur.text = now[0].debitur_reg_utama
//            binding.waktu.text = now[0].tgl_reg_utama
            binding.txtAntrianSaatIni.text = now[0].no_urut_pas
            binding.waktu.text = now[0].tgl_reg_utama
        }

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHistoryBinding.inflate(inflater, container, false)
}