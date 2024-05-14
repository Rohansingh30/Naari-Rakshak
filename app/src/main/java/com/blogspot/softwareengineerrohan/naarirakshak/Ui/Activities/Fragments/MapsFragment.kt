package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.blogspot.softwareengineerrohan.naarirakshak.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MapsFragment : Fragment() {

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        runBlocking {
            GlobalScope.launch(Dispatchers.IO) {
                try {

                    withContext(Dispatchers.Main) {
                        val sydney = LatLng(27.17510, 78.04217)

                        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker on Taj Mahal"))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                        googleMap.uiSettings.isZoomControlsEnabled = true
                        googleMap.uiSettings.isCompassEnabled = true
                        googleMap.uiSettings.isMyLocationButtonEnabled = true
                        googleMap.uiSettings.isMapToolbarEnabled = true
                        googleMap.uiSettings.isZoomGesturesEnabled = true
                        googleMap.uiSettings.isScrollGesturesEnabled = true
                        googleMap.uiSettings.isRotateGesturesEnabled = true
                        googleMap.uiSettings.isTiltGesturesEnabled = true
                        googleMap.uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = true

                        googleMap.isMyLocationEnabled = true
                    }
                } catch (e: Exception) {
                  Toast.makeText(this@MapsFragment.requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }



        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return@OnMapReadyCallback



        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}