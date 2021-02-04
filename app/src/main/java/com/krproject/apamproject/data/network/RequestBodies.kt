package com.krproject.apamproject.data.network

object RequestBodies {

    data class LoginBody(
        val email:String,
        val password:String
    )

    data class RegisterBody(
        val email:String,
        val password:String,
        val nama:String,
        val nohp:String,
        val nik:String,
        val noBpjs: String

    )

}