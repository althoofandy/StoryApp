package com.example.submission1_intermediate.maps

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.submission1_intermediate.R
import com.example.submission1_intermediate.databinding.FragmentMapsBinding
import com.example.submission1_intermediate.pref.UserPreference
import com.example.submission1_intermediate.ui.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding tidak ada!")
    private lateinit var viewMod: MapsViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "maps")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireContext().dataStore
        viewMod = viewModels<MapsViewModel> {
            ViewModelFactory(UserPreference.getInstance(pref))
        }.value

        viewMod.getUser().observe(viewLifecycleOwner) { user ->
            Log.d("result main maps:", user.token)
            viewMod.getMapsStory(user.token)

        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        viewMod.getMaps().observe(viewLifecycleOwner) {

            if(it.isNotEmpty()) {
                for (i in it) {
                    Glide.with(this)
                        .asBitmap()
                        .load(i.photoUrl)
                        .apply(RequestOptions().override(50, 50))
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                                val markerIcon = BitmapDescriptorFactory.fromBitmap(bitmap)

                                googleMap.addMarker(
                                    MarkerOptions()
                                        .position(LatLng(i.lat, i.lon))
                                        .title(i.name)
                                        .icon(markerIcon)
                                        .snippet(i.description)

                                )
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(i.lat,i.lon)))
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {

                            }
                        })


                }
            }
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}