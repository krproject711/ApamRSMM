package com.krproject.apamproject.data.response.poli

data class ResponseListPoli(
    val body: Body,
    val header: Header
)

data class Body(
    val `data`: List<Data>
)

data class Header(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)

data class Data(
    val id_dok: String,
    val id_ins: String,
    val nm_instalasi: String,
    val nmlengkap: String
)