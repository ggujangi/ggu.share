package com.ggu.share.upload.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ggu.share.R
import com.ggu.share.databinding.FragmentDialogUploadBinding
import com.ggu.share.utils.contract.CameraPermissionContract
import com.ggu.share.utils.contract.CameraPermissionContract.Companion.REQ_CODE_CAMERA_INTENT
import com.ggu.share.utils.contract.CameraPermissionContract.Companion.REQ_CODE_CAMERA_X
import com.ggu.share.utils.dialog.DialogUtils.showListAlertDialog
import com.ggu.share.utils.dialog.DialogUtils.MENU_CAMERA_INTENT
import com.ggu.share.utils.dialog.DialogUtils.MENU_CAMERA_X
import com.ggu.share.utils.dialog.DialogUtils.showButtonAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentDialogUploadBinding

    private val cameraPermissionResult =
        registerForActivityResult(CameraPermissionContract()) { (isGranted, reqCode) ->
            if (isGranted) {
                processCamera(reqCode)
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.request_camera_permission,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogUploadBinding.inflate(inflater, container, false).apply {
            clickListener = this@UploadDialogFragment
        }
        return binding.root
    }

    private fun requestCameraPermission(requestCode: Int) {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                processCamera(requestCode)
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showButtonAlertDialog(
                    context = requireContext(),
                    message = R.string.request_camera_permission,
                    positiveButton = R.string.request_permission_again,
                    positiveButtonListener = { _, _ ->
                        // 카메라 권한 획득
                        cameraPermissionResult.launch(requestCode)
                    })
            }
            else -> {
                cameraPermissionResult.launch(requestCode)
            }
        }
    }

    private fun processCamera(reqCode: Int) {
        when (reqCode) {
            REQ_CODE_CAMERA_INTENT -> {
            }
            REQ_CODE_CAMERA_X -> {
            }
            else -> {
                Toast.makeText(requireContext(), R.string.message_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.camera_btn -> {
                showListAlertDialog(
                    context = requireContext(),
                    items = R.array.dialog_camera_options
                ) { _, position ->
                    when (position) {
                        MENU_CAMERA_INTENT -> {
                            requestCameraPermission(REQ_CODE_CAMERA_INTENT)
                        }
                        MENU_CAMERA_X -> {
                            requestCameraPermission(REQ_CODE_CAMERA_X)
                        }
                    }
                }
            }
            else -> {

            }
        }
    }

    companion object {
        const val TAG = "UploadDialogFragment"
        fun newInstance(): UploadDialogFragment {
            val args = Bundle()
            val fragment = UploadDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

}