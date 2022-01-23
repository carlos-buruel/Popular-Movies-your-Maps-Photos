package com.example.movies.core

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.movies.R

object PermissionUtils {
	fun requestPermission(
		activity: AppCompatActivity,
		requestId: Int,
		permission: String,
		finishActivity: Boolean
	) {
		if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
			// Display a dialog with rationale.
			RationaleDialog.newInstance(requestId, finishActivity)
				.show(activity.supportFragmentManager, "dialog")
		} else {
			// Location permission has not been granted yet, request it.
			ActivityCompat.requestPermissions(activity, arrayOf(permission), requestId)
		}
	}

	fun isPermissionGranted(
		grantPermission: Array<String>, grantResults: IntArray, permission: String): Boolean {
		for (i in grantPermission.indices) {
			if (permission == grantPermission[i]) {
				return grantResults[i] == PackageManager.PERMISSION_GRANTED
			}
		}
		return false
	}

	class RationaleDialog: DialogFragment() {
		private var finishActivity = false
		override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
			val requestCode = arguments?.getInt(ARGUMENT_PERMISSION_REQUEST_CODE) ?: 0
			finishActivity = arguments?.getBoolean(ARGUMENT_FINISH_ACTIVITY) ?: false

			return AlertDialog.Builder(activity)
				.setMessage(R.string.permission_rationale_location)
				.setPositiveButton(android.R.string.ok) { _, _ ->
					ActivityCompat.requestPermissions(
						requireActivity(),
						arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
						requestCode
					)
					finishActivity = false
				}
				.setNegativeButton(android.R.string.cancel, null)
				.create()
		}

		override fun onDismiss(dialog: DialogInterface) {
			super.onDismiss(dialog)
			if (finishActivity) {
				Toast.makeText(activity, R.string.permission_required_toast, Toast.LENGTH_SHORT).show()
			}
			//activity?.finish()
		}

		companion object {
			private const val ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode"
			private const val ARGUMENT_FINISH_ACTIVITY = "finish"

			fun newInstance(requestCode: Int, finishActivity: Boolean): RationaleDialog {
				val arguments = Bundle().apply {
					putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode)
					putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
				}
				return RationaleDialog().apply {
					this.arguments = arguments
				}
			}
		}
	}

	class GpsDialog(
		private val result: ActivityResultLauncher<Intent>
	): DialogFragment() {
		override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
			return AlertDialog.Builder(activity)
				.setMessage(R.string.request_gps)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok) { _, _ ->
					val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
					result.launch(intent)
				}
				.setNegativeButton(android.R.string.cancel, null)
				.create()
		}

		companion object {
			fun newInstance(result: ActivityResultLauncher<Intent>): GpsDialog {
				return GpsDialog(result)
			}
		}
	}
}