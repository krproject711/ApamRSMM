package com.krproject.apamproject.ui.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.dokter.ListDokterViewModel
import com.krproject.apamproject.ui.jadwal_dokter.JadwalDokterViewModel
import com.krproject.apamproject.ui.jadwal_operasi.JadwalOperasiViewModel
import com.krproject.apamproject.ui.poli.ListPoliViewModel
import com.krproject.apamproject.ui.reservasi.ReservasiViewModel
import com.krproject.apamproject.ui.tiket.ListTiketViewModel

class ViewModelProviderFactory(
    val app: Application,
    val appRepository: AppRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(JadwalDokterViewModel::class.java)) {
            return JadwalDokterViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(JadwalOperasiViewModel::class.java)) {
            return JadwalOperasiViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(ListPoliViewModel::class.java)) {
            return ListPoliViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(ListDokterViewModel::class.java)) {
            return ListDokterViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(ReservasiViewModel::class.java)) {
            return ReservasiViewModel(app, appRepository) as T
        }

        if (modelClass.isAssignableFrom(ListTiketViewModel::class.java)) {
            return ListTiketViewModel(app, appRepository) as T
        }





        throw IllegalArgumentException("Unknown class name")
    }

}