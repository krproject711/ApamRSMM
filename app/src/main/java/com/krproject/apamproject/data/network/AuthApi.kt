package com.krproject.apamproject.data.network

import com.krproject.apamproject.data.response.DefaultResponse
import com.krproject.apamproject.data.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    //Method untuk registrasi user
    @FormUrlEncoded
    @POST("registrasi")
    suspend fun createUser(
        @Field("nik") nik: String,
        @Field("name") name: String,
        @Field("nohp") nohp: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : DefaultResponse

    //Method untuk login user
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email:String,
        @Field("password") password:String,
    ): Response<LoginResponse>
}