package com.example.submission1_intermediate.maps

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission1_intermediate.R
import com.example.submission1_intermediate.databinding.ActivityMapsBinding
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.ui.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var viewMod: MapsViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewMod = ViewModelProvider(this, ViewModelFactory(UserPreference.getInstance(dataStore)))[MapsViewModel::class.java]

        viewMod.getUser().observe(this) { user ->
            Log.d("result main maps:", user.token)
                viewMod.getMapsStory(user.token)

        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewMod.getMaps().observe(this) {
            if(it.isNotEmpty()) {
                for (i in it) {
                    googleMap.addMarker(MarkerOptions().position(LatLng(i.lat,i.lon)).title(i.name).snippet(i.description))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(i.lat,i.lon)))

                }
            }
        }
    }
}