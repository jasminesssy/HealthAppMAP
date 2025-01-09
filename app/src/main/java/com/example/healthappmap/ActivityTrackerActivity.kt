package com.example.healthappmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import android.graphics.Color

class ActivityTrackerActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 123
    private val routePoints = mutableListOf<LatLng>()

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val currentLatLng = LatLng(location.latitude, location.longitude)
            routePoints.add(currentLatLng)

            // Draw route on map
            if (routePoints.size > 1) {
                map?.addPolyline(
                    PolylineOptions()
                        .addAll(routePoints)
                        .color(Color.BLUE)
                        .width(5f)
                )
            }
        }

        // Modern LocationListener only requires onLocationChanged
        // Other methods are optional
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)

        // Initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Initialize location manager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check for location permissions
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            // Check both FINE and COARSE location permissions
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED -> {
                startLocationUpdates()
            }
            else -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    locationPermissionCode
                )
            }
        }
    }

    private fun startLocationUpdates() {
        try {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000, // Update every 5 seconds
                    10f,  // Or when moved 10 meters
                    locationListener
                )
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map?.isMyLocationEnabled = true
            map?.uiSettings?.isZoomControlsEnabled = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() &&
                grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            ) {
                startLocationUpdates()
            }
        }
    }
}