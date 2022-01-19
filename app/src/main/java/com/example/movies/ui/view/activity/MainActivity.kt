package com.example.movies.ui.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.movies.PermissionUtils.isPermissionGranted
import com.example.movies.PermissionUtils.requestPermission
import com.example.movies.R
import com.example.movies.core.Constant.nameMovies
import com.example.movies.core.Constant.nameMaps
import com.example.movies.core.Constant.nameImages
import com.example.movies.core.addFragment
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.ui.view.fragment.MoviesFragment

class MainActivity: AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private var lastFragment = ""
	private var permissionDenied = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.run {
			setSupportActionBar(toolbar)
			lastFragment = getString(R.string.title_movies)
			title = lastFragment
			addFragment(MoviesFragment())
		}
		enableLocation()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
		R.id.iMovies -> {
			if (lastFragment != nameMovies) {
				lastFragment = nameMovies
				addFragment(MoviesFragment())
			}
			true
		}
		R.id.iMaps -> {
			if (lastFragment != nameMaps) {
				lastFragment = nameMaps
				addFragment(MoviesFragment())
			}
			true
		}
		R.id.iImages -> {
			if (lastFragment != nameImages) {
				lastFragment = nameImages
				addFragment(MoviesFragment())
			}
			true
		}
		else -> super.onOptionsItemSelected(item)
	}

	private fun addFragment(fragment: Fragment) {
		addFragment(
			supportFragmentManager,
			fragment,
			binding.fragmentContainer.id)
	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<String>,
		grantResults: IntArray
	) {
		if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults)
			return
		}
		if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
			enableLocation()
		} else {
			permissionDenied = true
		}
	}

	private fun enableLocation() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
			== PackageManager.PERMISSION_GRANTED) {
			//TODO read location
		} else {
			requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
				Manifest.permission.ACCESS_FINE_LOCATION, true)
		}
	}

	companion object {
		private const val LOCATION_PERMISSION_REQUEST_CODE = 1
	}
}