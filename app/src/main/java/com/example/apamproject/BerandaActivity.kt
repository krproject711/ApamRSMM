package com.example.apamproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apamproject.databinding.ActivityBerandaBinding

class BerandaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerandaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

}