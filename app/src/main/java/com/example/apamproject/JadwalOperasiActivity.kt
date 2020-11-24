package com.example.apamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apamproject.databinding.ActivityJadwalOperasiBinding

class JadwalOperasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJadwalOperasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalOperasiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}