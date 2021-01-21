package com.krproject.apamproject.repository

import com.krproject.apamproject.data.network.RequestBodies
import com.krproject.apamproject.data.network.RetrofitInstance

class AppRepository {

//    suspend fun getPictures() = RetrofitInstance.picsumApi.getPictures()

    suspend fun loginUser(body: RequestBodies.LoginBody) = RetrofitInstance.loginApi.userLogin(body.email, body.password)
}