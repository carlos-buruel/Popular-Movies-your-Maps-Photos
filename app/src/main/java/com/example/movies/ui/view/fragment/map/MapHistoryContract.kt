package com.example.movies.ui.view.fragment.map

import com.example.movies.data.model.LatitudeLongitude

interface MapHistoryContract {
	fun getLocation(
		aLocation: ArrayList<LatitudeLongitude>,
		latitudeCenter: Double,
		longitudeCenter: Double
	)
}