package com.krproject.apamprojectnew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {
    // Durasi Splash Screen
    private val SPLASH_TIME: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Deklarasi Animasi Splash Screen
        val FADE_IN2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2)

        val logoText = findViewById(R.id.iv_logo_text) as ImageView

        // Set Animasi Splash Screen
        logoText.startAnimation(FADE_IN2)

        // Intent to Main Activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity2::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }, SPLASH_TIME)
    }
}