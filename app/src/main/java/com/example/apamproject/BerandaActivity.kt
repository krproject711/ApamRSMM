package com.example.apamproject

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.apamproject.databinding.ActivityBerandaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_beranda.*

class BerandaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerandaBinding
    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.beranda_navigation -> {
                val fragment = BerandaFragment.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.riwayat_navigation -> {
                val fragment = RiwayatFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        binding = ActivityBerandaBinding.inflate(layoutInflater)
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_beranda)
//        navigation.setOnNavigationItemReselectedListener { mOnNavigationItemSelectedListener }
//        val fragment = BerandaFragment.newInstance()
//        addFragment(fragment)
//
//
//        binding.antrian.setOnClickListener{
//            val intent = Intent(this, AntrianActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.daftar.setOnClickListener {
//            val intent = Intent(this, PoliklinikActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.jadwalDokter.setOnClickListener {
//            val intent = Intent(this, JadwalDokterActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.jadwalOperasi.setOnClickListener {
//            val intent = Intent(this, JadwalOperasiActivity::class.java)
//            startActivity(intent)
//        }

//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val view = binding.root
//        setContentView(view)


//    }

}