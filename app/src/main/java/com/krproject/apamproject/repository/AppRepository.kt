package com.krproject.apamproject.repository

import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.RetrofitInstance
import com.krproject.apamproject.data.response.LoginResponse
import com.krproject.apamproject.data.response.RegisterResponse
import com.krproject.apamproject.data.response.auth.ReservasiResponse
import com.krproject.apamproject.data.response.dokter.GetJadwalDokterResponse
import com.krproject.apamproject.data.response.dokter.ResponseGetDokter
import com.krproject.apamproject.data.response.operasi.GetJadwalOperasi
import com.krproject.apamproject.data.response.poli.ResponseListPoli
import com.krproject.apamproject.ui.tiket.ResponseTiket
import io.reactivex.Single
import okhttp3.ResponseBody

class AppRepository {

//    suspend fun getPictures() = RetrofitInstance.picsumApi.getPictures()

    fun loginUser(body: RequestBodies.LoginBody) : Single<LoginResponse>{
        return  RetrofitInstance.loginApi.userLogin(body.email, body.password)
    }

    fun registerUser(body: RequestBodies.RegisterBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.createUser(body.nik, body.nama, body.nohp,
            body.email, body.password, body.noBpjs)
    }

    fun reservasi(noRM: String, nama_pas: String, drName: String,
                  userPoli: String, transMethod: String,
                  email: String, tanggal: String) : Single<ReservasiResponse>{
        return  RetrofitInstance.loginApi.reservasi(noRM, nama_pas, drName, userPoli, transMethod, email, tanggal)
    }

    fun getJadwalDokter(search: String): Single<GetJadwalDokterResponse> = RetrofitInstance.loginApi.getJadwalDokter(search)

    fun getJadwalOperasi(search: String): Single<GetJadwalOperasi> = RetrofitInstance.loginApi.getJadwalOperasi(search)

    fun getListPoli(): Single<ResponseListPoli> = RetrofitInstance.loginApi.getPoliklinik()

    fun getListDokter(): Single<ResponseGetDokter> = RetrofitInstance.loginApi.getDokter()

    fun getListTiket(noRM: String): Single<ResponseTiket> = RetrofitInstance.loginApi.getTiket(noRM)

}