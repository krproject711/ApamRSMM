package com.krproject.apamproject.data.network

import com.krproject.apamproject.data.response.DefaultResponse
import com.krproject.apamproject.data.response.LoginResponse
import com.krproject.apamproject.data.response.RegisterResponse
import com.krproject.apamproject.data.response.dokter.GetJadwalDokterResponse
import com.krproject.apamproject.data.response.operasi.GetJadwalOperasi
import io.reactivex.Single
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
        @Field("password") password: String
    ) : Single<RegisterResponse>

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
        @Query("searchJadok") search: String
    ): Single<GetJadwalDokterResponse>

    @GET("jadop")
    fun getJadwalOperasi(
        @Query("searchJadop") search: String
    ): Single<GetJadwalOperasi>



}