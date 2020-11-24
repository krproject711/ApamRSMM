package com.example.apamproject

import android.content.Intent
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

        binding.antrian.setOnClickListener{
            val intent = Intent(this, AntrianActivity::class.java)
            startActivity(intent)
        }

        binding.daftar.setOnClickListener {
            val intent = Intent(this, PoliklinikActivity::class.java)
            startActivity(intent)
        }

        binding.jadwalDokter.setOnClickListener {
            val intent = Intent(this, JadwalDokterActivity::class.java)
            startActivity(intent)
        }

        binding.jadwalOperasi.setOnClickListener {
            val intent = Intent(this, JadwalOperasiActivity::class.java)
            startActivity(intent)
        }
    }

}