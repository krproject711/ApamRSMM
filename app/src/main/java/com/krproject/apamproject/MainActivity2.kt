package com.krproject.apamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.krproject.apamproject.data.UserPreferences
import com.krproject.apamproject.ui.auth.AuthActivity
import com.krproject.apamproject.ui.dashboard.BerandaActivity
import com.krproject.apamproject.ui.startNewActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val userPreferences = UserPreferences(this)

        userPreferences.authToken.asLiveData().observe(this, Observer {
            val activity = if(it == null) AuthActivity::class.java else BerandaActivity::class.java
            startNewActivity(activity)
        })
    }
}