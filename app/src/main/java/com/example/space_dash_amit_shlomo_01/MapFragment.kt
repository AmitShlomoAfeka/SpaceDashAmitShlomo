package com.example.space_dash_amit_shlomo_01.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.space_dash_amit_shlomo_01.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var location: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        location = arguments?.getParcelable("LOCATION")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        location?.let {
            updateMapLocation(it)
        }
    }

    fun updateMapLocation(location: LatLng) {
        googleMap?.clear()
        googleMap?.addMarker(MarkerOptions().position(location).title("High Score Location"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    companion object {
        fun newInstance(location: LatLng): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putParcelable("LOCATION", location)
            fragment.arguments = args
            return fragment
        }
    }
}
