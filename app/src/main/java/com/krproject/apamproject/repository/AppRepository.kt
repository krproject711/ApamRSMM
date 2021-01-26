package com.krproject.apamproject.repository

import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.RetrofitInstance
import com.krproject.apamproject.data.response.LoginResponse
import com.krproject.apamproject.data.response.RegisterResponse
import com.krproject.apamproject.data.response.dokter.GetJadwalDokterResponse
import com.krproject.apamproject.data.response.operasi.GetJadwalOperasi
import io.reactivex.Single

class AppRepository {

//    suspend fun getPictures() = RetrofitInstance.picsumApi.getPictures()

    fun loginUser(body: RequestBodies.LoginBody) : Single<LoginResponse>{
        return  RetrofitInstance.loginApi.userLogin(body.email, body.password)
    }

    fun registerUser(body: RequestBodies.RegisterBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.createUser(body.nik, body.nama, body.nohp,
            body.email, body.password)
    }

    fun getJadwalDokter(search: String): Single<GetJadwalDokterResponse> = RetrofitInstance.loginApi.getJadwalDokter(search)

    fun getJadwalOperasi(search: String): Single<GetJadwalOperasi> = RetrofitInstance.loginApi.getJadwalOperasi(search)




}