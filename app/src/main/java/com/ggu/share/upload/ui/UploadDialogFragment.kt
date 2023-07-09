package com.ggu.share.upload.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.ggu.share.R
import com.ggu.share.databinding.FragmentDialogUploadBinding
import com.ggu.share.upload.ui.CameraXActivity.Companion.KEY_REQUEST_TYPE
import com.ggu.share.upload.ui.CameraXActivity.Companion.REQ_CODE_TAKE_PHOTO
import com.ggu.share.utils.contract.CameraPermissionContract
import com.ggu.share.utils.contract.CameraPermissionContract.Companion.REQ_CODE_CAMERA_INTENT
import com.ggu.share.utils.contract.CameraPermissionContract.Companion.REQ_CODE_CAMERA_X
import com.ggu.share.utils.dialog.DialogUtils.showListAlertDialog
import com.ggu.share.utils.dialog.DialogUtils.MENU_CAMERA_INTENT
import com.ggu.share.utils.dialog.DialogUtils.MENU_CAMERA_X
import com.ggu.share.utils.dialog.DialogUtils.MENU_GET_CONTENT
import com.ggu.share.utils.dialog.DialogUtils.MENU_GET_MULTIPLE_CONTENT
import com.ggu.share.utils.dialog.DialogUtils.showButtonAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentDialogUploadBinding

    // Manifest.permission.CAMERA Permission
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

    // Take a Picture
    private val takePicturePreviewResult =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            dismiss()
        }

    // Camera X
    private val takePhotoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            dismiss()
        }

    // File Picker
    private val getContentResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            dismiss()
        }


    // File List Picker
    private val getMultipleContentResult =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            dismiss()
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
                takePicturePreviewResult.launch(null)
            }
            REQ_CODE_CAMERA_X -> {
                takePhotoResult.launch(
                    Intent(requireContext(), CameraXActivity::class.java).putExtra(
                        KEY_REQUEST_TYPE,
                        REQ_CODE_TAKE_PHOTO
                    )
                )
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
            R.id.gallery_btn -> {

            }
            R.id.file_btn -> {
                showListAlertDialog(
                    context = requireContext(),
                    items = R.array.dialog_file_options
                ) { _, position ->
                    when (position) {
                        MENU_GET_CONTENT -> {
                            getContentResult.launch("*/*")
                        }
                        MENU_GET_MULTIPLE_CONTENT -> {
                            getMultipleContentResult.launch("*/*")
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