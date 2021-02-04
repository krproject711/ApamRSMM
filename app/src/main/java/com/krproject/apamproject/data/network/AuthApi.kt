package com.krproject.apamproject.data.network

import com.krproject.apamproject.data.response.DefaultResponse
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
import retrofit2.Response
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

    @GET("listDokter")
    fun getDokter(): Single<ResponseGetDokter>

    @GET("tiket")
    fun getTiket(@Query("norm") noRM: String): Single<ResponseTiket>








}