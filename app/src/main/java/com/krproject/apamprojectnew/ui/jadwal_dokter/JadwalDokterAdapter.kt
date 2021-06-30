package com.krproject.apamprojectnew.ui.jadwal_dokter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.data.response.dokter.Data
import kotlinx.android.synthetic.main.item_jadwal_dokter.view.*

class JadwalDokterAdapter : RecyclerView.Adapter<JadwalDokterAdapter.ViewHolder>() {

    private var listDokter = ArrayList<Data>()
    private var namaDokter: String = ""
    private var namaKlinik: String = ""

    fun setJadwalDokter(data: List<Data>?){
        if (data == null) return
        listDokter.clear()
        listDokter.addAll(data)
    }

    fun clearJadwalDokter(){
        listDokter.clear()
    }

    fun filterBaseOnDokterName(dokterName: String){

        namaDokter = dokterName
        notifyDataSetChanged()
    }


    fun filterBaseOnNamaKlinik(klinikName: String){
        namaKlinik = klinikName
        notifyDataSetChanged()
    }

    fun sortByDayAsc(){
        val listDokterNew = ArrayList<Data>()
        val listHari = mutableListOf("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu")
        var start = 0
        for (j in  listHari.indices){
            for (i in listDokter.indices){
                val hari = listDokter[i].id_hari.trim().toLowerCase().replace("'", "")
                if (hari == listHari[j]){
                    listDokterNew.add(listDokter[i])
                }else{

                }
//                start++
            }
        }

        for (k in listDokterNew.indices){
            Log.d("CEKSORTEDHARI", "${listDokterNew[k].id_hari} ")
        }

        Log.d("CEKSORTEDHARI", "size: ${listDokterNew.size}")

        listDokter = listDokterNew

        notifyDataSetChanged()

    }

    fun sortByDayDesc(){
        val listDokterNew = ArrayList<Data>()
        val listHari = mutableListOf("minggu", "sabtu", "jumat", "kamis", "rabu", "selasa", "senin")
        var start = 0
        for (j in  listHari.indices){
            for (i in listDokter.indices){
                val hari = listDokter[i].id_hari.trim().toLowerCase().replace("'", "")
                if (hari == listHari[j]){
                    listDokterNew.add(listDokter[i])
                }else{

                }
//                start++
            }
        }

        for (k in listDokterNew.indices){
            Log.d("CEKSORTEDHARI", "${listDokterNew[k].id_hari} ")
        }

        Log.d("CEKSORTEDHARI", "size: ${listDokterNew.size}")

        listDokter = listDokterNew

        notifyDataSetChanged()

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
               tv_jam.text = "(${data.jam_mulai} - ${data.jam_selesai})"
               tv_kuota.text = "Kuota: ${data.kuotaOnline}"

               if (namaDokter.isNotEmpty()){
                   Log.d("CEKCOCOK", "namaDokter: $namaDokter  namaLengkap: ${data.nmlengkap}")
                   if (data.nmlengkap == namaDokter){
                       val params = itemView.layoutParams
                       params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                       params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                       itemView.layoutParams = params
                       itemView.visibility = View.VISIBLE
                   }else{
                       itemView.visibility = View.GONE
                       itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
                   }

               }else{
                   val params = itemView.layoutParams
                   params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                   params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                   itemView.layoutParams = params
                   itemView.visibility = View.VISIBLE
               }

               if (itemView.visibility == View.VISIBLE){
                   Log.d("CEKVISIBLE", "bind: visible")
                   if (namaKlinik.isNotEmpty()){
                       if (data.nm_instalasi == namaKlinik){
                           val params = itemView.layoutParams
                           params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                           params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                           itemView.layoutParams = params
                           itemView.visibility = View.VISIBLE
                       }else{
                           itemView.visibility = View.GONE
                           itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
                       }

                   }
                   else{
                       val params = itemView.layoutParams
                       params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                       params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                       itemView.layoutParams = params
                       itemView.visibility = View.VISIBLE
                   }
               }
           }
            Log.d("CEKSIZE", "getItemCount: ${listDokter.size}")
        }
    }
}