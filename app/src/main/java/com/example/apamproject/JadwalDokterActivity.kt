package com.example.apamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apamproject.databinding.ActivityJadwalDokterBinding

class JadwalDokterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJadwalDokterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalDokterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}