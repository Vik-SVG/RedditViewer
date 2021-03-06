package com.vkpriesniakov.redditviewer.image_worker

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


private const val READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
private const val WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE


class WriteImagePermission(
    fragment: Fragment,
) {

    private val context = fragment.context as Context
    private val view = fragment.view as View

    private var mPermissionAllowed = false

    private val requestPermissionLauncher =
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            when {
                isGranted -> mPermissionAllowed = true
                !isGranted -> {
                    mPermissionAllowed = false
//                    if (!shouldShowRequestPermissionRationale(WRITE_STORAGE_PERMISSION))
                    showSettingsSnackbar(activitySettingsResult)
                }
            }
        }

    private val activitySettingsResult =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            mPermissionAllowed = selfPermissionCheck()
        }

    private fun showSettingsSnackbar(
        activityResult: ActivityResultLauncher<Intent>
    ) {
        val snackbar = Snackbar.make(
            view,
            "To save image you must provide permission",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Settings") {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri: Uri =
                Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            activityResult.launch(intent)
        }
        snackbar.show()
    }

    fun checkPermissionToSaveImage(): Boolean {
        return if (selfPermissionCheck()) {
            mPermissionAllowed = true
            mPermissionAllowed
        } else {
            requestPermissionLauncher.launch(WRITE_STORAGE_PERMISSION)
            mPermissionAllowed
        }
    }

    private fun selfPermissionCheck(): Boolean {
        return PermissionChecker.checkSelfPermission(
            context,
            WRITE_STORAGE_PERMISSION
        ) == PermissionChecker.PERMISSION_GRANTED
    }
}