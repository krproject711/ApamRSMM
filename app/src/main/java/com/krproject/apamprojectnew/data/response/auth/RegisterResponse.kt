package com.krproject.apamprojectnew.data.response.auth

data class RegisterResponse(
    val header: HeaderRegister
)

data class HeaderRegister(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)