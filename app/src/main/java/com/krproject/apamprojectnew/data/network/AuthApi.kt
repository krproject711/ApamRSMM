package com.krproject.apamprojectnew.data.network

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
import retrofit2.http.*

interface AuthApi {

    //Method untuk registrasi user
    @FormUrlEncoded
    @POST("register")
    fun createUser(
        @Field("nik") nik: String,
        @Field("nama") name: String,
        @Field("nohp") nohp: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nobpjs") bpjs: String
    ) : Single<RegisterResponse>

    @FormUrlEncoded
    @POST("editProfil")
    fun editProfile(
        @Field("noRM") noRm: String,
        @Field("no_ktp") noKtp: String,
        @Field("nama_pas") namaPasien: String,
        @Field("tmpt_lahir") tempatLahir: String,
        @Field("tgl_lahir") tgl_lahir: String,
        @Field("gol_darah") goldar: String,
        @Field("no_telp") noTelp: String,
        @Field("work") pekerjaan: String,
        @Field("edu") pendidikan: String,
        @Field("statu_nikah") statusNikah: String,
        @Field("orangtua") orangtua: String,
        @Field("jenis_kelamin") jenisKelamin: String,
        @Field("address") address: String
    ) : Single<RegisterResponse>

    @FormUrlEncoded
    @POST("reservasi")
    fun reservasi(
        @Field("noRM") noRM: String,
        @Field("nama_pas") nama_pas: String,
        @Field("drName") drName: String,
        @Field("userPoli") userPoli: String,
        @Field("transMethod") transMethod: String,
        @Field("email") email: String,
        @Field("tglReservasi")tanggal: String
    ) : Single<ReservasiResponse>

    @FormUrlEncoded
    @POST("uploadImg")
    fun uploadImgKK(
        @Field("noRM") noRM: String,
        @Field("kkImg") kkImg: String?,
        @Field("imgKet") imgKet: String,
    ) : Single<RegisterResponse>

    @FormUrlEncoded
    @POST("uploadImg")
    fun uploadImgKTP(
        @Field("noRM") noRM: String,
        @Field("ktpImg") ktpImg: String?,
        @Field("imgKet") imgKet: String,
    ) : Single<RegisterResponse>

    @FormUrlEncoded
    @POST("uploadImg")
    fun uploadImgBPJS(
        @Field("noRM") noRM: String,
        @Field("bpjsImg") bpjsImg: String?,
        @Field("imgKet") imgKet: String,
    ) : Single<RegisterResponse>

    @FormUrlEncoded
    @POST("verifikasiEmail")
    fun verifikasiEmail(
        @Field("nik") nik: String,
        @Field("email") email: String?,
        @Field("password") password: String,
    ) : Single<VerifikasiEmailResponse>

    @FormUrlEncoded
    @POST("forgetPassword")
    fun forgetPassword(
        @Field("nik") nik: String,
        @Field("email") email: String?,
    ) : Single<VerifikasiEmailResponse>

    //Method untuk login user
    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password:String,
    ): Single<LoginResponse>

    //Method untuk login user
    @GET("jadok")
    fun getJadwalDokter(
        @Query("scrjadok") search: String
    ): Single<GetJadwalDokterResponse>

    @GET("jadop")
    fun getJadwalOperasi(
        @Query("searchJadop") search: String
    ): Single<GetJadwalOperasi>

    @GET("listPoli")
    fun getPoliklinik(): Single<ResponseListPoli>

    @GET("listdokter")
    fun getDokter(): Single<ResponseGetDokter>

    @GET("tiket")
    fun getTiket(@Query("norm") noRM: String): Single<ResponseTiket>


}