package com.krproject.apamproject.data.response.dokter

data class GetJadwalDokterResponse(
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
    val id_hari: String,
    val nm_instalasi: String,
    val nmlengkap: String,
    val jam_mulai: String,
    val jam_selesai: String,
)