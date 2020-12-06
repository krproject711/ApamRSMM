package com.example.apamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apamproject.databinding.ActivityAntrianBinding

class AntrianActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAntrianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAntrianBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backButton.setOnClickListener{
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
        }
    }
}