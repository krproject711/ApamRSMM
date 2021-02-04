package com.krproject.apamproject.data.response.auth

data class ReservasiResponse(
    val body: Body,
    val header: Header
)

data class Body(
    val `data`: String
)

data class Header(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)