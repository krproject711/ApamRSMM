package com.example.apamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {
    // Durasi Splash Screen
    private val SPLASH_TIME: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Deklarasi Animasi Splash Screen
        val FADE_IN = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val FADE_IN2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2)

        val logo = findViewById(R.id.iv_logo) as ImageView
        val logoText = findViewById(R.id.iv_logo_text) as ImageView

        // Set Animasi Splash Screen
        logo.startAnimation(FADE_IN)
        logoText.startAnimation(FADE_IN2)

        // Intent to Main Activity
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME)
    }
}