package com.krproject.apamproject.ui.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krproject.apamproject.repository.AppRepository
import com.krproject.apamproject.ui.auth.AuthViewModel
import com.krproject.apamproject.ui.jadwal_dokter.JadwalDokterViewModel
import com.krproject.apamproject.ui.jadwal_operasi.JadwalOperasiViewModel

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




        throw IllegalArgumentException("Unknown class name")
    }

}