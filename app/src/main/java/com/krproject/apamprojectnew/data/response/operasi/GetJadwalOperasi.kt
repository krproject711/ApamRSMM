package com.krproject.apamprojectnew.data.response.operasi

data class GetJadwalOperasi(
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
    val nm_brg: String,
    val nm_instalasi: String,
    val nmlengkap: String,
    val tgl_operasi: String,
    var hari: String =""
)