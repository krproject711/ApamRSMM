package com.krproject.apamprojectnew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        val userPreferences = UserPreferences(this)
//
//        userPreferences.authToken.asLiveData().observe(this, Observer {
//            val activity = if(it == null) AuthActivity::class.java else BerandaActivity::class.java
//            startNewActivity(activity)
//        })
    }
}