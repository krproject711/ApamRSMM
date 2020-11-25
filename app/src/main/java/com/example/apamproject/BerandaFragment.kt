package com.example.apamproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apamproject.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {

    fun onCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    companion object{
        fun newInstance(): BerandaFragment{
            val fragment = BerandaFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

