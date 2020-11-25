package com.example.apamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apamproject.databinding.ActivityJadwalOperasiBinding
import kotlinx.android.synthetic.main.activity_jadwal_operasi.*

class JadwalOperasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJadwalOperasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJadwalOperasiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.viewpagerMain.adapter = MyPagerAdapter(supportFragmentManager)
        binding.tabsMain.setupWithViewPager(viewpager_main)
    }
}