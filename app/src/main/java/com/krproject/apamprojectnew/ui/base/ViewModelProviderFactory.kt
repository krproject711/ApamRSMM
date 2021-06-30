package com.krproject.apamprojectnew.ui.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krproject.apamprojectnew.repository.AppRepository
import com.krproject.apamprojectnew.ui.auth.AuthViewModel
import com.krproject.apamprojectnew.ui.dokter.ListDokterViewModel
import com.krproject.apamprojectnew.ui.jadwal_dokter.JadwalDokterViewModel
import com.krproject.apamprojectnew.ui.jadwal_operasi.JadwalOperasiViewModel
import com.krproject.apamprojectnew.ui.poli.ListPoliViewModel
import com.krproject.apamprojectnew.ui.reservasi.ReservasiViewModel
import com.krproject.apamprojectnew.ui.tiket.ListTiketViewModel

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