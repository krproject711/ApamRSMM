package com.example.apamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.apamproject.databinding.ActivityWelcomeScreenBinding
import com.example.apamproject.databinding.FragmentDaftarPoliklinikBinding
import com.example.apamproject.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentDaftarPoliklinikBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDaftarPoliklinikBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        binding.masukButton.setOnClickListener{
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.daftarButton.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
    }
}