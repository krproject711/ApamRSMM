package com.krproject.apamproject.data.response

data class RegisterResponse(
    val header: HeaderRegister
)

data class HeaderRegister(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)