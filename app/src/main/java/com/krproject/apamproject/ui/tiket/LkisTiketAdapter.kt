package com.krproject.apamproject.ui.tiket

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.krproject.apamproject.R
import kotlinx.android.synthetic.main.fragment_history.view.*
import kotlinx.android.synthetic.main.item_tiket.view.*
import kotlinx.android.synthetic.main.item_tiket.view.debitur
import kotlinx.android.synthetic.main.item_tiket.view.tv_dokter
import kotlinx.android.synthetic.main.item_tiket.view.tv_poliklinik
import kotlinx.android.synthetic.main.item_tiket.view.waktu

class LkisTiketAdapter(private val activity: Activity) : RecyclerView.Adapter<LkisTiketAdapter.ViewHolder>() {

    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick()
    }

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tiket, parent, false)
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
               tv_dokter.text = data.nm_instalasi
               tv_poliklinik.text = data.no_urut_pas
               waktu.text = data.tgl_reg_utama
               debitur.text = data.debitur_reg_utama

               setOnClickListener {

                   mOnItemClickListener?.onItemClick()
               }
           }
            Log.d("CEKSIZE", "getItemCount: ${listDokter.size}")
        }
    }
}