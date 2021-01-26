package com.krproject.apamproject.ui.jadwal_dokter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krproject.apamproject.R
import com.krproject.apamproject.data.response.dokter.Data
import kotlinx.android.synthetic.main.item_jadwal_dokter.view.*

class JadwalDokterAdapter : RecyclerView.Adapter<JadwalDokterAdapter.ViewHolder>() {

    private var listDokter = ArrayList<Data>()

    fun setJadwalDokter(data: List<Data>?){
        if (data == null) return
        listDokter.clear()
        listDokter.addAll(data)
    }

    fun clearJadwalDokter(){
        listDokter.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal_dokter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listDokter[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {

        Log.d("CEKSIZE", "getItemCount: ${listDokter.size}")
        return listDokter.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
           with(itemView){
               poliklinik.text = data.nm_instalasi
               dokter.text = data.nmlengkap
               hari.text = data.id_hari
               tv_jam.text = "(${data.jam_mulai} - ${data.jam_selesai}"
           }
            Log.d("CEKSIZE", "getItemCount: ${listDokter.size}")
        }
    }
}