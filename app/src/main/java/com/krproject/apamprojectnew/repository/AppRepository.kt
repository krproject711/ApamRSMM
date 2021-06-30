package com.krproject.apamprojectnew.repository

import com.krproject.apamprojectnew.data.network.RequestBodies
import com.krproject.apamprojectnew.data.network.RetrofitInstance
import com.krproject.apamprojectnew.data.response.LoginResponse
import com.krproject.apamprojectnew.data.response.auth.RegisterResponse
import com.krproject.apamprojectnew.data.response.auth.ReservasiResponse
import com.krproject.apamprojectnew.data.response.dokter.GetJadwalDokterResponse
import com.krproject.apamprojectnew.data.response.dokter.ResponseGetDokter
import com.krproject.apamprojectnew.data.response.operasi.GetJadwalOperasi
import com.krproject.apamprojectnew.data.response.poli.ResponseListPoli
import com.krproject.apamprojectnew.data.response.verifikasiEmail.VerifikasiEmailResponse
import com.krproject.apamprojectnew.ui.tiket.ResponseTiket
import io.reactivex.Single

class AppRepository {

//    suspend fun getPictures() = RetrofitInstance.picsumApi.getPictures()

    fun loginUser(body: RequestBodies.LoginBody) : Single<LoginResponse>{
        return  RetrofitInstance.loginApi.userLogin(body.email, body.password)
    }

    fun verifikasiEmail(body: RequestBodies.VerikasiEmailBody): Single<VerifikasiEmailResponse>{
        return  RetrofitInstance.loginApi.verifikasiEmail(body.nik, body.email, body.password)
    }

    fun forgetPassword(body: RequestBodies.ForgetPasswordBody): Single<VerifikasiEmailResponse>{
        return  RetrofitInstance.loginApi.forgetPassword(body.nik, body.email)
    }

    fun registerUser(body: RequestBodies.RegisterBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.createUser(body.nik, body.nama, body.nohp,
            body.email, body.password, body.noBpjs)
    }

    fun editProfile(body: RequestBodies.EditProfileBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.editProfile(body.noRm, body.noKtp, body.namaPasien,
            body.tempatLahir, body.tglLahir, body.goldar, body.noTelpon,body.pekerjaan,body.pendidikan,
            body.statusNikah,body.orangtua,body.jenisKelamin,body.address
        )
    }



    fun reservasi(noRM: String, nama_pas: String, drName: String,
                  userPoli: String, transMethod: String,
                  email: String, tanggal: String) : Single<ReservasiResponse>{
        return  RetrofitInstance.loginApi.reservasi(noRM, nama_pas, drName, userPoli, transMethod, email, tanggal)
    }

    fun uploadImgKK(body: RequestBodies.UploadImageBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.uploadImgKK(
            body.noRm, body.kkImg, "kk")
    }

    fun uploadImgKTP(body: RequestBodies.UploadImageBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.uploadImgKTP(
            body.noRm, body.kkImg, "ktp")
    }

    fun uploadImgBPJS(body: RequestBodies.UploadImageBody) : Single<RegisterResponse>{
        return  RetrofitInstance.loginApi.uploadImgBPJS(
            body.noRm, body.kkImg, "bpjs")
    }

    fun getJadwalDokter(search: String): Single<GetJadwalDokterResponse> = RetrofitInstance.loginApi.getJadwalDokter(search)

    fun getJadwalOperasi(search: String): Single<GetJadwalOperasi> = RetrofitInstance.loginApi.getJadwalOperasi(search)

    fun getListPoli(): Single<ResponseListPoli> = RetrofitInstance.loginApi.getPoliklinik()

    fun getListDokter(): Single<ResponseGetDokter> = RetrofitInstance.loginApi.getDokter()

    fun getListTiket(noRM: String): Single<ResponseTiket> = RetrofitInstance.loginApi.getTiket(noRM)

}