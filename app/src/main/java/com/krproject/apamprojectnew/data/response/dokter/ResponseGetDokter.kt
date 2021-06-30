package com.krproject.apamprojectnew.data.response.dokter

data class ResponseGetDokter(
    val body: BodyGetDokter,
    val header: HeaderGetDokter
)

data class BodyGetDokter(
    val `data`: List<DataGetDokter>
)

data class HeaderGetDokter(
    val result: String,
    val resultCode: String,
    val resultMessage: String
)

data class DataGetDokter(
    val id_user: String,
    val nmlengkap: String,
    val id_ins: String
)