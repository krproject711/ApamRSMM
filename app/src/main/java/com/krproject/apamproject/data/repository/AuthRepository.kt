package com.krproject.apamproject.data.repository

import com.krproject.apamproject.data.UserPreferences
import com.krproject.apamproject.data.network.AuthApi

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
): BaseRepository() {

    suspend fun login (
        email: String,
        password: String
    ) = safeApiCall {
        api.userLogin(email, password)
    }

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

    suspend fun createUser(
        nik: String,
        name: String,
        nohp : String,
        email: String,
        password: String
    ) = safeApiCall {
        api.createUser(nik, name, nohp, email, password)
    }
}