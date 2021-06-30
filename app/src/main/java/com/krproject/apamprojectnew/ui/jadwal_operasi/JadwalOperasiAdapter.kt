package com.krproject.apamprojectnew.ui.jadwal_operasi

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.krproject.apamprojectnew.R
import com.krproject.apamprojectnew.data.response.operasi.Data
import kotlinx.android.synthetic.main.item_jadwal_dokter.view.dokter
import kotlinx.android.synthetic.main.item_jadwal_operasi.view.*
import java.text.SimpleDateFormat

class JadwalOperasiAdapter : RecyclerView.Adapter<JadwalOperasiAdapter.ViewHolder>() {

    private var listDokter = ArrayList<Data>()
    private var namaDokter: String = ""
    private var namaKlinik: String = ""


    fun setJadwalDokter(data: List<Data>?){
        if (data == null) return
        listDokter.clear()

        for (i in data.indices){
            data[i].hari = getDay(data[i].tgl_operasi)
            Log.d("CEKHARI", "date: ${data[i].tgl_operasi} hari: ${getDay(data[i].tgl_operasi)}")
        }
        listDokter.addAll(data)
    }

    fun filterBaseOnDokterName(dokterName: String){

//        listDokter.filter {
//            it.nmlengkap == dokterName
//        }

        namaDokter = dokterName
        notifyDataSetChanged()
    }


 fun filterBaseOnNamaKlinik(klinikName: String){

//        listDokter.filter {
//            it.nmlengkap == dokterName
//        }

        namaKlinik = klinikName
        notifyDataSetChanged()
    }


    fun sortByDayAsc(){
        val listDokterNew = ArrayList<Data>()
        val listHari = mutableListOf("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu")
        var start = 0
        for (j in  listHari.indices){
            for (i in listDokter.indices){
                val hari = listDokter[i].hari.trim().toLowerCase().replace("'", "")
                if (hari == listHari[j]){
                    listDokterNew.add(listDokter[i])
                }else{

                }
//                start++
            }
        }

        for (k in listDokterNew.indices){
            Log.d("CEKSORTEDHARI", "${listDokterNew[k].hari} ")
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
                val hari = listDokter[i].hari.trim().toLowerCase().replace("'", "")
                if (hari == listHari[j]){
                    listDokterNew.add(listDokter[i])
                }else{

                }
//                start++
            }
        }

        for (k in listDokterNew.indices){
            Log.d("CEKSORTEDHARI", "${listDokterNew[k].hari} ")
        }

        Log.d("CEKSORTEDHARI", "size: ${listDokterNew.size}")

        listDokter = listDokterNew

        notifyDataSetChanged()

    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_jadwal_operasi,
            parent,
            false
        )
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
        fun bind(data: Data) {
           with(itemView){
               jenisOperasi.text = data.nm_brg
               dokter.text = data.nmlengkap
               tglOperasi.text = data.tgl_operasi
               
           }

            Log.d("CEKNAMADOKTER", "bind: $namaDokter")
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
//                Log.d("CEKNULL", "notNull: ")

            }else{
                val params = itemView.layoutParams
                params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                itemView.layoutParams = params
                itemView.visibility = View.VISIBLE
//                Log.d("CEKNULL", "Null: ")
            }

            if (itemView.visibility == View.VISIBLE){
                Log.d("CEKVISIBLE", "bind: visible")
                if (namaKlinik.isNotEmpty()){
                    if (data.nm_brg == namaKlinik){
                        val params = itemView.layoutParams
                        params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                        itemView.layoutParams = params
                        itemView.visibility = View.VISIBLE
                    }else{
                        itemView.visibility = View.GONE
                        itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
                    }
//                Log.d("CEKNULL", "notNull: ")

                }
                else{
                    val params = itemView.layoutParams
                    params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
                    params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
                    itemView.layoutParams = params
                    itemView.visibility = View.VISIBLE
//                Log.d("CEKNULL", "Null: ")
                }
            }



            Log.d("CEKSIZE", "getItemCount: ${listDokter.size}")
        }
    }

    fun addDayToList(data: List<Data>?): List<Data>?{
        if (data!= null){
            for (i in data.indices){
                data[i].hari = getDay(data[i].tgl_operasi)
                Log.d("CEKHARI", "date: ${data[i].tgl_operasi} hari: ${getDay(data[i].tgl_operasi)}")
            }
        }
        return data
    }

    @SuppressLint("SimpleDateFormat")
    fun getDay(date: String): String {
        val dateFormat = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
        val dates = dateFormat.parse(date)
        val day = android.text.format.DateFormat.format("EEEE", dates)
        return day as String
    }
}