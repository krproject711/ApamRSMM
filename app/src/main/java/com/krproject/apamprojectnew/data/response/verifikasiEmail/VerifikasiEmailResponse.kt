package com.krproject.apamprojectnew.data.response.verifikasiEmail

data class VerifikasiEmailResponse(
    val body: List<Any>,
    val header: Header
)

data class Header(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)