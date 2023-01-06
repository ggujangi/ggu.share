package com.ggu.share.upload.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ggu.share.databinding.FragmentDialogUploadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDialogUploadBinding.inflate(inflater, container, false).root

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