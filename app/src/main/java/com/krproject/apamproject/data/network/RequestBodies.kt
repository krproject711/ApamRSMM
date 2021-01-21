package com.krproject.apamproject.data.network

object RequestBodies {

    data class LoginBody(
        val email:String,
        val password:String
    )

}