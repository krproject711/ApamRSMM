package com.krproject.apamprojectnew.data.response

data class UserRequest(
    val access_token: String,
    val id: Int,
    val nik: String,
    val nama: String,
    val nohp: String,
    val email: String,
    val password: String
)