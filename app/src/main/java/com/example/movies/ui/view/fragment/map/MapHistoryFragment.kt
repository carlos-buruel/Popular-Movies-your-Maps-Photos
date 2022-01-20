package com.example.movies.ui.view.fragment.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movies.R
import com.example.movies.data.model.LatitudeLongitude
import com.example.movies.data.network.LocationService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapHistoryFragment: Fragment(), MapHistoryContract {
	private var mapFragment: GoogleMap? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_history_map, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		val map = childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
		map?.getMapAsync {
			this.mapFragment = it
			LocationService().getLocation(this)
		}
	}

	override fun getLocation(
		aLocation: ArrayList<LatitudeLongitude>,
		latitudeCenter: Double,
		longitudeCenter: Double
	) {
		val centerLocation = LatLng(latitudeCenter, longitudeCenter)
		mapFragment?.run {
			moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 18f))
			for ((index, location) in aLocation.withIndex()) {
				val forMarker = LatLng(location.latitude ?: 0.0, location.longitude ?: 0.0)
				addMarker(
					MarkerOptions().position(forMarker).title("Posicion $index")
				)
			}
		}
	}
}