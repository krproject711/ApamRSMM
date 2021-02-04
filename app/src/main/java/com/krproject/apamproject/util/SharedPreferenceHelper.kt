package com.krproject.apamproject.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class SharedPreferenceHelper(context: Context) {

    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor

    private val myPrefName = context.packageName

    init {
        sharedPreferences = context.getSharedPreferences(myPrefName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setToken(token: String){
        editor.putString(TOKEN, token).apply()
    }

    fun getToken() = sharedPreferences.getString(TOKEN, "")

    fun setEmail(email: String){
        editor.putString(EMAIL, email).apply()
    }

    fun getEmail() = sharedPreferences.getString(EMAIL, "")

    fun setNoRm(noRm: String){
        editor.putString(NO_RM, noRm).apply()
    }

    fun getNoRm() = sharedPreferences.getString(NO_RM, "")



    fun clearAllDataShared(){
        editor.clear().apply()
    }

    companion object{
        const val TOKEN = "token"
        const val EMAIL = "email"
        const val NO_RM = "no_rm"

    }
}