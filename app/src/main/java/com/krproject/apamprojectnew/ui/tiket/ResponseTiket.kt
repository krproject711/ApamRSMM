package com.krproject.apamprojectnew.ui.tiket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseTiket(
    val body: Body,
    val header: Header
): Parcelable

@Parcelize
data class Body(
    val `data`: List<Data>,
    val now: Now
): Parcelable

@Parcelize
data class Header(
    val result: String,
    val resultCode: String,
    val resultMessage: String
): Parcelable

@Parcelize
data class Data(
    val debitur_reg_utama: String,
    val dokter_reg_meta: String,
    val id_pol_reg: String,
    val nama_penanggung_reg: String,
    val nm_instalasi: String,
    val no_urut_pas: String,
    val tgl_reg_utama: String
): Parcelable

@Parcelize
data class Now(
    val Tgl_reg_utama: String,
    val no_urut_pas: String
): Parcelable