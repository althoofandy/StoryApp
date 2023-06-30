package com.example.submission1_intermediate.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.submission1_intermediate.R
import com.example.submission1_intermediate.databinding.ActivityMainBinding
import com.example.submission1_intermediate.maps.MapsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.navView.background = null

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        binding.btnUpload.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        }
    }


