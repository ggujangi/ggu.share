package com.ggu.share.utils.contract

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

class CameraPermissionContract : ActivityResultContract<Int, Pair<Boolean, Int>>() {

    private var requestCode: Int = REQ_CODE_NONE

    override fun createIntent(context: Context, input: Int): Intent {
        this.requestCode = input

        return Intent(ActivityResultContracts.RequestMultiplePermissions.ACTION_REQUEST_PERMISSIONS)
            .putExtra(
                ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS,
                arrayOf(Manifest.permission.CAMERA)
            )
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Boolean, Int> {
        if (intent == null || resultCode != Activity.RESULT_OK) return Pair(false, requestCode)

        val isGranted =
            intent.getIntArrayExtra(
                ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS
            )?.any { result ->
                result == PackageManager.PERMISSION_GRANTED
            } == true

        return Pair(isGranted, requestCode)
    }

    companion object {
        const val REQ_CODE_NONE = -1
        const val REQ_CODE_CAMERA_INTENT = 100
        const val REQ_CODE_CAMERA_X = 200
    }
}
