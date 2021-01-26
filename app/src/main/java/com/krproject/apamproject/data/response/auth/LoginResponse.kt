package com.krproject.apamproject.data.response

data class LoginResponse(
    val body: Body = Body(),
    val header: Header
)

data class Body(
    val apiToken: String = "",
    val nama_pas: String = "",
    val no_rm_pas: String = ""
)

data class Header(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)

