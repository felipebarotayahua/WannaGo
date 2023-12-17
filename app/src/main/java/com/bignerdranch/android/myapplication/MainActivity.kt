package com.bignerdranch.android.myapplication
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.firebase.database.FirebaseDatabase





class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private val coordinatesList = mutableListOf<Coordinates>()
    private lateinit var adapter: CoordinatesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = CoordinatesAdapter(coordinatesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Initialize Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val initialLocation = LatLng(37.7749, -122.4194) // Example location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12f))
        googleMap.uiSettings.isZoomControlsEnabled = true

        googleMap.setOnMapClickListener { latLng ->
            addMarker(latLng)
        }
    }

    private fun addMarker(latLng: LatLng) {
        if (::googleMap.isInitialized) {
            val markerOptions = MarkerOptions().position(latLng).title("Lat: ${latLng.latitude}, Long: ${latLng.longitude}")
            googleMap.addMarker(markerOptions)
            coordinatesList.add(Coordinates(latLng.latitude, latLng.longitude))
            adapter.notifyItemInserted(coordinatesList.size - 1)
        } else {
            Log.e("MainActivity", "Google Map is not initialized")
        }
        // Add data to Firebase
        val coordinates = Coordinates(latLng.latitude, latLng.longitude)
        val databaseReference = FirebaseDatabase.getInstance().getReference("Coordinates")
        databaseReference.push().setValue(coordinates)
    }
}

//            // Write location data to Firebase
//            val locationData = LocationData(location.latitude, location.longitude, "Marker Title")
//            ref.child("https://secondproject-ff4ac-default-rtdb.firebaseio.com/").setValue(locationData)