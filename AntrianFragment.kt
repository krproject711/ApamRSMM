package com.krproject.apamprojectnew.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.databinding.FragmentAntrianBinding
import com.krproject.apamprojectnew.ui.base.BaseFragment
import com.krproject.apamprojectnew.ui.tiket.ResponseTiket

class AntrianFragment : BaseFragment<FragmentAntrianBinding>() {
   lateinit var responseTiket: ResponseTiket
   var position: Int = 0
   val args: AntrianFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_antrianFragment2_to_berandaFragment3)
        }

        responseTiket = args.responseTiket
        position = args.index

        Log.d("CEKDATA", "responseTiket: $responseTiket position: $position")

        binding.tvTanggal.text = responseTiket.body.data[position].tgl_reg_utama
        binding.tvTiketPoliklinik.text = responseTiket.body.data[position].nm_instalasi + "(${responseTiket.body.now[0].nmlengkap})"
        binding.tvTiketDebitur.text = responseTiket.body.data[position].debitur_reg_utama
        binding.tvTiketAntrian.text = responseTiket.body.data[position].no_urut_pas

        binding.tvTiketAntrianSekarang.text = responseTiket.body.now[0].no_urut_pas
//        binding.tvDokter.text = responseTiket.body.now[0].nmlengkap
//        binding.tvPoliklinik.text = responseTiket.body.now[0].nm_instalasi
//        binding.debitur.text = responseTiket.body.now[0].debitur_reg_utama
//        binding.waktu.text = responseTiket.body.now[0].tgl_reg_utama
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAntrianBinding.inflate(inflater, container, false)
}