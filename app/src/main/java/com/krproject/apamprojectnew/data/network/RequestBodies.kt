package com.krproject.apamprojectnew.data.network

object RequestBodies {

    data class LoginBody(
        val email:String,
        val password:String
    )

    data class VerikasiEmailBody(
        val nik: String,
        val email:String,
        val password:String
    )

    data class ForgetPasswordBody(
        val nik: String,
        val email:String
    )

    data class RegisterBody(
        val email:String,
        val password:String,
        val nama:String,
        val nohp:String,
        val nik:String,
        val noBpjs: String
    )

    data class EditProfileBody(
        val noRm: String,
        val noKtp:String,
        val namaPasien:String,
        val tempatLahir:String,
        val tglLahir:String,
        val goldar:String,
        val noTelpon: String,
        val pekerjaan: String,
        val pendidikan: String,
        val statusNikah: String,
        val orangtua: String,
        val jenisKelamin: String,
        val address: String
    )

    data class UploadImageBody(
        val noRm: String,
        val kkImg:String?,
        val imgKet:String
    )
}